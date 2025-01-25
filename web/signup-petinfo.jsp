<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sign Up Page - Pet Information</title>
  <link rel="stylesheet" href="css/signup-petinfo.css">
  <link rel="stylesheet" href="https://use.typekit.net/oov2wcw.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Chelsea+Market">
</head>

<body>
  <nav>
    <div><img src="images/logo.png" alt="Logo"></div>
    <div>
      <a href="landing.jsp">HOME</a>
      <a href="landing.jsp">ABOUT</a>
      <a href="landing.jsp">CONTACT US</a>
      <a href="login.jsp" class="button">LOGIN</a>
    </div>
  </nav>

  <div class="container">

    <div class="left-section">
      <h1>KHO<br> VETERINARY<br> CLINIC</h1>
      <div class="alternate-links">
        <a href="signup-employees.jsp">Sign Up as Employee</a><br>
        <p>Not a Pet Owner?</p>
      </div>
    </div>

    <div class="right-section">
      <h2>Create Account</h2>

      <p>Your pet's care starts here!</p>
      
      <div class="form">
      <form action="SignUpPetOwnerServlet" method="POST">

        <div class = "form-row">
        <div class="form-group">
          <label for="petname">Pet Name*</label>
          <input type="text" id="petname" name="petname" required>
        </div>
        <div class="radio">
            <label>Gender*</label>
            <div class="radio-group">
              <input type="radio" id="male" name="gender" value="male" required>
              <label for="male">Male</label>
              <input type="radio" id="female" name="gender" value="female" required>
              <label for="female">Female</label>
            </div>
          </div>
        </div>

        <div class = "form-row">
        <div class="form-group">
            <label for="species">Pet Species*</label>
            <input type="text" id="species" name="species" required>
          </div>
        <div class="form-group">
          <label for="breed">Pet Breed (Optional)</label>
          <input type="text" id="breed" name="breed">
        </div>
        </div>

        <div class="form-group">
          <label for="birthdate">Birthday (Optional)</label>
          <input type="date" id="birthdate" name="birthdate">
        </div>

        <div class="form-group captcha">
          <label for="captcha">Enter Captcha</label>
          <div class="captcha-container">
            <span class="generated">AaZ312</span>
            <input type="text" id="captcha" name="captcha" required>
          </div>
        </div>

        <div class = "button-group">
        <button type="button" class="button2" onclick="window.history.back()">BACK</button>
        <button type="submit" class="button3" name="action" value="signup">SIGN UP</button>
        </div>
        
      </form>
    </div>
    </div>

  </div>
</body>

</html>
