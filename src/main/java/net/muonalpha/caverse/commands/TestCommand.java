package net.muonalpha.caverse.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class TestCommand
{
	public static LiteralArgumentBuilder<CommandSourceStack> register()
	{
		return Commands.literal("time").requires((player) -> { return player.hasPermission(2); })
				.then
				(
					Commands.literal("set")
					.then
					(
						Commands.literal("day").executes((player) -> { return printLog("time set day"); })
					)
					.then
					(
						Commands.literal("noon").executes((player) -> { return printLog("time set noon"); })
					)
					.then
					(
						Commands.literal("night").executes((player) -> { return printLog("time set night"); })
					)
					.then
					(
						Commands.literal("midnight").executes((player) -> { return printLog("time set midnight"); })
					)
				)
				.then
				(
					Commands.literal("query")
					.then
					(
						Commands.literal("daytime").executes((player) -> { return printLog("time query daytime"); })
					)
					.then
					(
						Commands.literal("gametime").executes((player) -> { return printLog("time query gametime"); })
					)
					.then
					(
						Commands.literal("day").executes((player) -> { return printLog("time query day"); })
					)
				);
	}

	public static LiteralArgumentBuilder<CommandSourceStack> register2()
	{
		return Commands.literal("time2").requires((player) -> { return player.hasPermission(2); })
				.then
				(
					Commands.literal("set")
					.then
					(
						Commands.literal("day").executes((player) -> { return printLog("time set day"); })
					)
					.then
					(
						Commands.literal("noon").executes((player) -> { return printLog("time set noon"); })
					)
					.then
					(
						Commands.literal("night").executes((player) -> { return printLog("time set night"); })
					)
					.then
					(
						Commands.literal("midnight").executes((player) -> { return printLog("time set midnight"); })
					)
				)
				.then
				(
					Commands.literal("query")
					.then
					(
						Commands.literal("daytime").executes((player) -> { return printLog("time query daytime"); })
					)
					.then
					(
						Commands.literal("gametime").executes((player) -> { return printLog("time query gametime"); })
					)
					.then
					(
						Commands.literal("day").executes((player) -> { return printLog("time query day"); })
					)
				);
	}

	private static int printLog(String message)
	{
		System.out.println(message);

		return 0;
	}
}
