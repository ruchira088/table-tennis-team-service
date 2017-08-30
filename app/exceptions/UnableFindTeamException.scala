package exceptions

case class UnableFindTeamException(teamName: String) extends Exception
{
  override def getMessage = s"Unable to find a team named \"$teamName\""
}
