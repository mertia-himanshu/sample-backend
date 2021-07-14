package org.tmt.sample.core.models

case class GreetResponse(greeting: String)
object GreetResponse {
  def apply(userInfo: UserInfo): GreetResponse = new GreetResponse(s"Hello user: ${userInfo.firstname} ${userInfo.lastname}!!!")
}
case class AdminGreetResponse(greeting: String)
object AdminGreetResponse {
  def apply(userInfo: UserInfo): AdminGreetResponse =
    new AdminGreetResponse(s"Hello admin user: ${userInfo.firstname} ${userInfo.lastname}!!!")
}
