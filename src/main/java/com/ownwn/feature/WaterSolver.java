package com.ownwn.feature;

import com.ownwn.event.ClientTickEvent;
import meteordevelopment.orbit.EventHandler;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.*;
import java.util.function.Predicate;

public class WaterSolver {
    public static final int HEIGHT = 20;
    public static final int WIDTH = 21;

    public static boolean water = false;
    public static boolean inRoom = false;

    @EventHandler
    public static void onTick(ClientTickEvent.TickClassic event) {
        if (!inRoom) return;

        BlockPos playerPos = MinecraftClient.getInstance().player.getBlockPos();
        World world = MinecraftClient.getInstance().world;


        Optional<BlockPos> chestLocation = findBlock(world, playerPos, Blocks.CHEST, 10);
        if (chestLocation.isEmpty()) return;

        BlockPos chestPos = chestLocation.get();
//        if (chestPos.getY() != 56) return;

        boolean positive = false;
        Direction forwardsRelative = null;
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos newPos = chestPos.offset(direction, 3).offset(Direction.UP, 4);
            if (world.getBlockState(newPos).isOf(Blocks.POLISHED_ANDESITE)) {
                positive = true;
                forwardsRelative = direction;
                break;
            }
        }
        if (!positive) return;


        // directions are relative facing towards chest from room entrance

        Direction rightRelative = forwardsRelative.rotateYClockwise();
        Direction leftRelative = forwardsRelative.rotateYCounterclockwise();
        Direction backwardsRelative = rightRelative.rotateYClockwise();

        BlockPos bottomLeft = chestPos.offset(forwardsRelative, 4).offset(leftRelative, 10).offset(Direction.UP, 4);

        BlockPos topRight = chestPos.offset(forwardsRelative, 4).offset(rightRelative, 10).offset(Direction.UP, 23);

        MinecraftClient.getInstance().world.setBlockState(bottomLeft, Blocks.STONE.getDefaultState());

        BlockPos[][] flowArray = new BlockPos[WIDTH][HEIGHT];
        BlockPos[][] behindArray = new BlockPos[WIDTH][HEIGHT];




        Map<Block, Integer> blockToNumberMapping = new HashMap<>();

        blockToNumberMapping.put(Blocks.QUARTZ_BLOCK, 4);
        blockToNumberMapping.put(Blocks.DIAMOND_BLOCK, 5);
        blockToNumberMapping.put(Blocks.EMERALD_BLOCK, 6);
        blockToNumberMapping.put(Blocks.GOLD_BLOCK, 7);
        blockToNumberMapping.put(Blocks.COAL_BLOCK, 8);
        blockToNumberMapping.put(Blocks.TERRACOTTA, 9);




        // 0 = solid
        // 1 = always air
        // 4-9 could change :(
        int[][] stateArray = new int[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {

                BlockPos currentBlock = bottomLeft.offset(rightRelative, x).offset(Direction.UP, y);
                BlockState state = world.getBlockState(currentBlock);
                Block backBlock = world.getBlockState(currentBlock.offset(forwardsRelative)).getBlock();

                flowArray[x][y] = currentBlock;
                behindArray[x][y] = currentBlock.offset(forwardsRelative);

                if (state.isOf(Blocks.POLISHED_ANDESITE)) {
                    stateArray[x][y] = 0;
                    continue;
                }



                if (blockToNumberMapping.containsKey(state.getBlock())) {
                    stateArray[x][y] = blockToNumberMapping.get(state.getBlock());
                    continue;
                }
//                } else if (blockToNumberMapping.containsKey(backBlock)) {
//                    stateArray[x][y] = blockToNumberMapping.get(backBlock);
//                    continue;
//                }

                if (!state.isOf(Blocks.AIR) && !state.isOf(Blocks.WATER) && !state.isOf(Blocks.GLOWSTONE)) continue;


//                if (backBlock == Blocks.PRISMARINE || backBlock == Blocks.SEA_LANTERN) {
                    stateArray[x][y] = 1;
//                    System.out.println("air at: " + currentBlock);
//                    world.setBlockState(currentBlock, Blocks.REDSTONE_BLOCK.getDefaultState());
                    continue;
//                }
            }
        }

        BlockPos entrance = bottomLeft.offset(rightRelative, 10).offset(Direction.UP, 19);

//        if (water) {
            List<BlockPos> adjacents = findAirFromEntrance(world, entrance);

            for (BlockPos adjacent : adjacents) {
                BlockState adjacentState = world.getBlockState(adjacent);
                if (!adjacentState.isOf(Blocks.AIR) && !adjacentState.isOf(Blocks.GLOWSTONE)) continue;

//                world.setBlockState(adjacent, Blocks.GLOWSTONE.getDefaultState());
                world.addParticle(ParticleTypes.HAPPY_VILLAGER, adjacent.getX() + 0.5, adjacent.getY() + 0.5, adjacent.getZ() + 0.5, 0, 0, 0);
            }

//            water = false;
//        }

        if (DevTesting.devMode) {
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    System.out.println(stateArray[x][y]);
                }
            }
            DevTesting.devMode = false;
        }



        // 21 across, 20 up
        // water in middle


    }

    private static List<BlockPos> getAdjacentAirBlocks(World world, BlockPos current) {
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.DOWN};

        List<BlockPos> adjacentAirBlocks = new ArrayList<>();

        for (Direction direction : directions) {
            BlockPos adjacent = current.offset(direction);
            BlockState state = world.getBlockState(adjacent);

            if (state.isOf(Blocks.AIR) || state.isOf(Blocks.GLOWSTONE)) {
                adjacentAirBlocks.add(adjacent);
            }
        }

        return adjacentAirBlocks;
    }

    public static List<BlockPos> findAirFromEntrance(World world, BlockPos entrance) {
        List<BlockPos> foundAirBlocks = new ArrayList<>();
        Queue<BlockPos> queue = new LinkedList<>();

        queue.add(entrance);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            if (!foundAirBlocks.contains(current) && entrance.getY() - current.getY() <= 19) {
                foundAirBlocks.add(current);

                List<BlockPos> adjacents = getAdjacentAirBlocks(world, current);

                for (BlockPos adjacent : adjacents) {
                    if (!foundAirBlocks.contains(adjacent)) {
                        queue.add(adjacent);
                    }
                }
            }
        }

        return foundAirBlocks;
    }





    public static Optional<BlockPos> findBlock(World world, BlockPos playerPos, Block block, int range) {
        Predicate<BlockPos> matchBlock = pos -> {
            BlockState state = world.getBlockState(pos);
            return state.isOf(block);
        };

        return BlockPos.findClosest(playerPos, range, range, matchBlock);
    }


    @EventHandler
    public static void onTick2(ClientTickEvent.TickSecond event) {
        BlockPos playerPos = MinecraftClient.getInstance().player.getBlockPos();
        World world = MinecraftClient.getInstance().world;

        if (findBlock(world, playerPos, Blocks.STICKY_PISTON, 10).isEmpty()) {
            inRoom = false;
            return;
        }
        inRoom = true;
    }

    public static void onRender(WorldRenderContext context) {
    }











}
