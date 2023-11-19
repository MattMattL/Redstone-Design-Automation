package net.muonalpha.caverse.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class TestCommand
{
	public static LiteralArgumentBuilder<CommandSourceStack> wrapper()
	{
		return Commands.literal("test").requires((source) -> { return source.hasPermission(2); })
				.then(Commands.literal("set")
					.then(Commands.literal("day").executes((source) -> { return printLog("time set day"); }))
					.then(Commands.literal("noon").executes((source) -> { return printLog("time set noon"); }))
					.then(Commands.literal("night").executes((source) -> { return printLog("time set night"); }))
					.then(Commands.literal("midnight").executes((source) -> { return printLog("time set midnight"); }))
				)
				.then(Commands.literal("query")
					.then(Commands.literal("daytime").executes((source) -> { return printLog("time query daytime"); }))
					.then(Commands.literal("gametime").executes((source) -> { return printLog("time query gametime"); }))
					.then(Commands.literal("day").executes((source) -> { return printLog("time query day"); }))
				);
	}

	public static LiteralArgumentBuilder<CommandSourceStack> wrapper2()
	{
		return Commands.literal("test2").requires((source) -> { return source.hasPermission(2); })
				.then(Commands.literal("set")
					.then(Commands.literal("day").executes((source) -> { return printLog("time set day"); }))
					.then(Commands.literal("noon").executes((source) -> { return printLog("time set noon"); }))
					.then(Commands.literal("night").executes((source) -> { return printLog("time set night"); }))
					.then(Commands.literal("midnight").executes((source) -> { return printLog("time set midnight"); }))
				)
				.then(Commands.literal("query")
					.then(Commands.literal("daytime").executes((source) -> { return printLog("time query daytime"); }))
					.then(Commands.literal("gametime").executes((source) -> { return printLog("time query gametime"); }))
					.then(Commands.literal("day").executes((source) -> { return printLog("time query day"); }))
				);
	}

	private static int printLog(String message)
	{
		System.out.println(message);

		return 0;
	}
}
