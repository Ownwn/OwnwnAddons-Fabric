package com.ownwn

import com.ownwn.config.NewConfig
import net.fabricmc.api.ClientModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object OwnwnAddonsFabric : ClientModInitializer {
	@JvmStatic
	val logger: Logger = LoggerFactory.getLogger("ownwnaddons")

	override fun onInitializeClient() {
		NewConfig.load()
	}
}