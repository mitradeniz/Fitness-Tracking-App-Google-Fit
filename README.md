# Fitness Tracker App

This Android application retrieves fitness data from Google Fit APIs, including step count, activity duration, and calories burned, and displays the data in graphical form. 

## Features

- **Step Count**: Track the number of steps taken each day.
- **Activity Duration**: Monitor the duration of physical activities.
- **Calories Burned**: Keep track of the calories burned during various activities.
- **Graphical Display**: Visualize fitness data using interactive charts and graphs.

## Prerequisites

- Android Studio
- Android device or emulator running Android 6.0 (Marshmallow) or higher
- Google account with Google Fit data

## Setup Instructions

1. **Clone the Repository**
    ```sh
    git clone https://github.com/mitradeniz/Fitness-Tracking-App-Google-Fit.git
    cd Fitness-Tracking-App-Google-Fit
    ```

2. **Open in Android Studio**
    - Launch Android Studio and select `Open an existing Android Studio project`.
    - Navigate to the cloned repository and open it.

3. **Configure Google Fit API or Using SignIn With Google**
    - If you are going to log in with a Google account, you do not need to perform Google Cloud Console operations.
    - Go to the [Google Cloud Console](https://console.cloud.google.com/).
    - Create a new project or select an existing project.
    - Enable the Google Fit API for your project.
    - Configure OAuth consent screen and create OAuth 2.0 credentials.
    - Add the SHA-1 fingerprint of your app to the OAuth 2.0 credentials.

5. **Add Credentials to Your App**
    - Open `app/build.gradle` and add the following dependency:
      ```kotlin dsl
      implementation ("com.google.android.gms:play-services-fitness:20.0.0")
      implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
      implementation (libs.play.services.fitness)
      ```

6. **Run the App**
    - Connect your Android device or start an emulator.
    - Click the `Run` button in Android Studio.

## Usage

- Upon launching the app, sign in with your Google account to grant permissions to access Google Fit data.
- Navigate through the app to view your step count, activity duration, and calories burned.
- The data is displayed in various graphical formats for easy interpretation.

## Libraries Used

- [Google Fit API](https://developers.google.com/fit)
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) for graphical representation of data

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For questions or suggestions, please open an issue in the repository or contact [mitradenizkaratas@gmail.com](mailto:mitradenzikaratas@gmail.com).

---

Happy coding! Stay fit!
