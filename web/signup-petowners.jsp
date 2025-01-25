<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Signup Page - Pet Owners</title>
  <link rel="stylesheet" href="css/signup-petowners.css">
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
      
      <div class = "form">
      <form action="SignUpPetOwnerServlet" method="POST">

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

        <div class="form-group">
          <label for="address">Address*</label>
          <input type="address" id="address" name="address" required>
        </div>

        <div class="form-group">
          <label for="password">Password*</label>
          <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
          <label for="confirmpassword">Confirm Password*</label>
          <input type="confirmpassword" id="confirmpassword" name="confirmpassword" required>
        </div>

        <button type="submit" class="nextbutton" name="action" value="next">NEXT</button>
        
      </form>
    </div>
    </div>

  </div>
</body>

</html>
