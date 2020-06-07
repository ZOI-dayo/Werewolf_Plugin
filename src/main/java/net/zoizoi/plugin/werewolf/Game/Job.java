package net.zoizoi.plugin.werewolf.Game;

public class Job {
  private String jobName;
  private String camp;

  public Job(String jobName) {
    this.jobName = jobName;
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
    return this.jobName;
  }

  public String getCamp() {
    return this.camp;
  }

  public String Work(Player player) {
    switch (camp) {
      case "Prophet":
        if (player.getLife()) {
          if (player.getJob().jobName == "Werewolf") {
            return "この人は人狼です";
          } else {
            return "この人は人狼ではありません";
          }
        } else {
          return "この人は死んでいます";
        }
      case "Necromancer":
        if (player.getLife()) {
          return "この人は生きています";
        } else {
          if (player.getJob().jobName == "Werewolf") {
            return "この人は人狼です";
          } else {
            return "この人は人狼ではありません";
          }
        }
      default:
        return "あなたの役職は特別な仕事がありません";
    }
  }
}
// 特殊効果



