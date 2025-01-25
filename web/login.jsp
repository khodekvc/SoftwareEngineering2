<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login Page</title>
  <link rel="stylesheet" href="css/login.css">
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
      <a href="signup-petowners.jsp" class="button">SIGN UP</a>
    </div>
  </nav>

  <div class="container">

    <div class="left-section">
      <h1>KHO<br> VETERINARY<br> CLINIC</h1>
    </div>

    <div class="right-section">
      <h2>Welcome Back!</h2>

      <p>Login to your account</p>
      
      <form action="LoginServlet" method="POST">

        <div class="form-group">
          <label for="email">Email *</label>
          <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
          <label for="password">Password *</label>
          <input type="password" id="password" name="password" required>
        </div>

        <div class="forgot">
          <a href="#">Forgot Password?</a>
        </div>

        <div class="form-group captcha">
          <label for="captcha">Enter Captcha</label>
          <div class="captcha-container">
            <span class="generated">AaZ312</span>
            <input type="text" id="captcha" name="captcha" required>
          </div>
        </div>

        <button type="submit" class="button2">LOGIN</button>

      </form>
    </div>

  </div>
</body>

</html>
