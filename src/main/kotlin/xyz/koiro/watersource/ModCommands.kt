package xyz.koiro.watersource

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.server.command.CommandManager.*
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import xyz.koiro.watersource.world.attachment.ModAttachmentTypes


object ModCommands {
    const val RESTORE_ALL_TARGET_ERROR_TRANS_KEY = "watersource.command.error.restore_all_target_must_be_player"
    /** # Usage in mc
     * `$ waterlevel set <player> <level> <saturation>`
     *
     * `$ waterlevel restoreAll (<player>)`
     *  */
    private val waterlevelCommand = CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource>, commandRegistryAccess: CommandRegistryAccess, registrationEnvironment: RegistrationEnvironment ->
            dispatcher
                .register(
                    literal("waterlevel")
                        .then(
                            literal("set")
                                .then(
                                    argument("player", EntityArgumentType.players())
                                        .then(
                                            argument("level", IntegerArgumentType.integer(0, 20))
                                                .then(argument("saturation", IntegerArgumentType.integer(0, 20))
                                                    .executes {
                                                        val players = EntityArgumentType.getPlayers(it, "player")
                                                        val level = IntegerArgumentType.getInteger(it, "level")
                                                        val saturation =
                                                            IntegerArgumentType.getInteger(it, "saturation")
                                                        players.forEach { player ->
                                                            val data =
                                                                player.getAttachedOrCreate(ModAttachmentTypes.WATER_LEVEL)
                                                            data.setData(level, saturation, 0f)
                                                            data.updateToClient(player)
                                                        }
                                                        1
                                                    })
                                        )
                                )
                        )
                        .then(literal("restoreAll").executes {
                            val player = it.source.player
                            if (player != null) {
                                restoreAll(player)
                                1
                            }else {
                                throw SimpleCommandExceptionType(Text.translatable(RESTORE_ALL_TARGET_ERROR_TRANS_KEY)).create()
                            }
                        }.then(argument("player", EntityArgumentType.players()).executes {
                            val players = EntityArgumentType.getPlayers(it, "player")
                            players.forEach { player ->
                                restoreAll(player)
                            }
                            1
                        }))
                )
        }

    fun initialize() {
        CommandRegistrationCallback.EVENT.register(waterlevelCommand)
    }

    private fun restoreAll(player: ServerPlayerEntity) {
        val data = player.getAttachedOrCreate(ModAttachmentTypes.WATER_LEVEL)
        data.setData(data.maxLevel, data.maxSaturation, 0f)
        data.updateToClient(player)
    }
}