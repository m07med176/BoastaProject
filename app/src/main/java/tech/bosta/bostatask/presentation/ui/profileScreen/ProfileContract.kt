package tech.bosta.bostatask.presentation.ui.profileScreen





data class ProfileScreenActions(
    val onClick: (() -> Unit)?=null
)

sealed interface ProfileEventUI{
    object RequestRandomUser:ProfileEventUI
    data class RequestProfile(val userId:Int):ProfileEventUI
}