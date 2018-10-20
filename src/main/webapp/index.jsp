<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

	<head>
		
		<title>Web Interface</title>
		<style>

 body {background-color: #708090;} 
div.gallery {
   margin: 5px;
   border: 1px solid #ccc;
   width: 180px;
   height: 240px;
   float: left;
}

div.gallery:hover {
   border: 1px solid #777;
}

div.gallery img {
   width: 100%;
   height: 100%;
   object-fit: cover;
}

div.desc {
   padding: 15px;
   text-align: center;
}
</style>
	</head>
	<body>
		<!--Title-->
		<h1 id = "main_title" >Web Interface</h1>
		<h3 id = "welcome">Welcome: User!</h3>
		<!--Friends List.-->
		<br />
		<br />
		<hr />
       <div class="gallery">
   <a href="GetData?data=mygalleries">
     <img src="folder.png" alt="Folder" width="400" height="200">
   </a>
   <div class="desc">My Galleries</div>
  </div>

        <div class="gallery">
   <a href="GetData?data=getFriendGalleries">
     <img src="folder.png" alt="Folder" width="400" height="200">
   </a>
   <div class="desc">Friend Galleries</div>
  </div>
  
          <div class="gallery">
   <a href="GetData?data=getFriends">
     <img src="folder.png" alt="Folder" width="400" height="200">
   </a>
   <div class="desc">My Friends</div>
  </div>
  
            <div class="gallery">
   <a href="SetData?data=preparefolder">
     <img src="addfolder.png" alt="Folder" width="400" height="200">
   </a>
   <div class="desc">New Gallery</div>
  </div>

	</body>
</html>