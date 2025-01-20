<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Signup Page - Confirmation</title>
  <link rel="stylesheet" href="css/signup-accesscode.css">
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
      <a href="login.jsp" class="button">SIGN UP</a>
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
      <h2>Access Code <br>
    Confirmation</h2>

      <div class="form">
      <form action="#" method="POST">
        
          <div class="form-group">
            <label for="fname">Please enter the access code sent to your email by the doctor to confirm your role.</label>
            <input type="number" id="accesscode" name="accesscode" required>
          </div>
        

        <button type="submit" class="button2">SUBMIT</button>
        
      </form>
    </div>
    </div>

  </div>
</body>

</html>
