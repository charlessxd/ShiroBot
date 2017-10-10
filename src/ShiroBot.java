import com.jagrosh.jdautilities.commandclient.CommandClientBuilder;
import com.jagrosh.jdautilities.commandclient.examples.AboutCommand;
import com.jagrosh.jdautilities.commandclient.examples.PingCommand;
import com.jagrosh.jdautilities.commandclient.examples.ShutdownCommand;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.security.auth.login.LoginException;

import commands.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import utils.Listener;
import utils.SetPlaying;

public class ShiroBot {
    public static void main(String[] args) throws IOException, LoginException, IllegalArgumentException, RateLimitedException
    {

        List<String> list = Files.readAllLines(Paths.get("config.txt"));
        String token = list.get(0);
        String ownerId = list.get(1);

        EventWaiter waiter = new EventWaiter();
        CommandClientBuilder client = new CommandClientBuilder();
        Listener listener = new Listener();
        SetPlaying setPlaying = new SetPlaying();

        client.useDefaultGame();
        client.setOwnerId(ownerId);
        client.setPrefix("s_");
        client.addCommands(

                // command to show information about the bot
                new AboutCommand(Color.BLUE, "a retarded weeb bot.",
                        new String[]{"Ranks","Urban Dictionary Command","MyAnimeList Command"},
                        new Permission[]{Permission.ADMINISTRATOR}),
                        new SetName(),
                        new SendBroadcast(),
                        new GetGirl(),
                        new JoinVoice(),
                        new LeaveVoice(),
                        new UrbanDictionary(),
                        new PingCommand(),
                        new GetAnime(),
                        new GetBitcoin(),
                        new GetRanks(),
                        new Enable(),
                        new Disable(),
                        new getSettings(),
                        new GetOsuProfile(),
                        new ShutdownCommand());

        // start getting a bot account set up
        new JDABuilder(AccountType.BOT)
                // set the token
                .setToken(token)
                // set the game for when the bot is loading
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                // add the listeners
                .addEventListener(waiter)
                .addEventListener(client.build())
                .addEventListener(listener)
                .addEventListener(setPlaying)
                // start it up!
                .buildAsync();
    }
}
