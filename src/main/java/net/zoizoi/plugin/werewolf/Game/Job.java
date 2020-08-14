package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.System.PluginConfig;

public enum Job {
    Citizen("Citizen"),
    Prophet("Prophet"),
    Necromancer("Necromancer"),
    Werewolf("Werewolf"),
    Betrayer("Betrayer");






    private String jobName;
    private String camp;
    // private boolean isUsed;

    private Job(final String jobName) {
        this.jobName = jobName;
        // this.isUsed = true;
        switch (jobName) {
            case "Citizen":     // 市民
            case "Prophet":     // 予言者
            case "Necromancer": // 霊媒師
                camp = "Village";
                break;
            case "Werewolf":    // 人狼
            case "Betrayer":    // 狂人
                camp = "Wolf";
                break;
            default:
                camp = "None";
                break;
        }
    }

    public String getJobName() {
        if (jobName != null) {
            return jobName;
        } else {
            return "";
        }
    }

    public String getJobNameJapanese() {
        return PluginConfig.config.getString("japanese.jobs." + jobName);
    }


    public String getCamp() {
        return this.camp;
    }

    public String Work(GamePlayer gamePlayer) {
        // if (!isUsed) {
        // isUsed = true;
        if (jobName == "Prophet") {
            if (gamePlayer.getLife()) {
                if (gamePlayer.getJob().jobName == "Werewolf") {
                    return "この人は人狼です";
                } else {
                    return "この人は人狼ではありません";
                }
            } else {
                return "この人は死んでいます";
            }
        } else if (jobName.equals("Necromancer")) {
            if (gamePlayer.getLife()) {
                return "この人は生きています";
            } else {
                if (gamePlayer.getJob().jobName == "Werewolf") {
                    return "この人は人狼です";
                } else {
                    return "この人は人狼ではありません";
                }
            }
        } else {
            return "あなたの役職は特別な仕事がありません";
        }
    /*
    }else{
        return "もう仕事はできません";
    }

     */
    }
}
// 特殊効果



