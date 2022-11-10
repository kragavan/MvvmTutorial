### Sample Login App using Android MVVM architecture

In this sample Android app we use a login screen and user authentication flow to demonstrate Android MVVM architecture. I have setup a mock backend running on NodeJs for login API.

### System Design
The sample app follows the MVVM architecure pattern as per official Android documentation. We use ViewModels to modify UI state changes and notify user actions to Repository. Repository classes determine remote or local datasource based on the user requests and provide us the required response.

![alt text for screen readers](/MvvmDesign.png "MVVM Architecture")