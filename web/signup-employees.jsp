<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Signup Page - Employees</title>
  <link rel="stylesheet" href="css/signup-employees.css">
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
        <a href="signup-petowners.jsp">Sign Up as Pet Owner</a><br>
        <p>Not an Employee?</p>
      </div>
    </div>

    <div class="right-section">
      <h2>Create Account</h2>

      <p>Become part of our team!</p>
      
      <div class="form">
      <form action="SignUpEmployeeServlet" method="POST">

        
        <div class="form-row">
          <div class="form-group">
            <label for="fname">First Name*</label>
            <input type="text" id="fname" name="fname" required>
          </div>
          <div class="form-group">
            <label for="fname">Last Name*</label>
            <input type="text" id="lname" name="lname" required>
          </div>
        </div>


        <div class="form-row">
          <div class="form-group">
            <label for="email">Email*</label>
            <input type="email" id="email" name="email" required>
          </div>
          <div class="form-group">
            <label for="contact">Contact Number*</label>
            <input type="contact" id="contact" name="contact" required>
          </div>
        </div>

        <div class="radio">
          <label>Choose Role*</label>
          <div class="radio-group">
            <input type="radio" id="clinicians" name="role" value="clinician" required>
            <label for="clinicians">Clinicians</label>
            <input type="radio" id="fdeskstaff" name="role" value="staff" required>
            <label for="fdeskstaff">Front Desk Staff</label>
          </div>
        </div>

        <div class="form-group">
          <label for="password">Password*</label>
          <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
          <label for="confirmpassword">Confirm Password*</label>
          <input type="confirmpassword" id="confirmpassword" name="confirmpassword" required>
        </div>

        <div class="form-group captcha">
          <label for="captcha">Enter Captcha</label>
          <div class="captcha-container">
            <img class="generated" src="SignUpEmployeeServlet" alt="CAPTCHA" id="captchaImage">
            <input type="text" id="captcha" name="captcha" class="input" required>
          </div>
        </div>
    

        <button type="submit" class="button2" name="action" value="signup">SIGN UP</button>
        
      </form>
    </div>
    </div>

  </div>
</body>

</html>
