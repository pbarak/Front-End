<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html><head>
  <title>Motorcycle Maintenance: Removing Spark Plugs</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
 body{
               margin-left:0px;
               margin-right:0px;
               margin-top:0px;
               margin-bottom:0px;
               }

       #body{
       background-color:green;
       }
       
       label {
          	   margin-left:auto;
               margin-right:auto;
               margin-top:auto;
               margin-bottom:auto;
               width:40%;
               		}
		
		#textinput {
		 display: inline-block;
		 width:60%;

		}
	   #button {
	   	  display: inline-block;
	   	  width:10%;
	    } 	
	    
	    #addForm {
	           display:block;
               width:97%;
               height:10%;
               margin-left:auto;
               margin-right:auto;
               margin-top:1%;
               margin-bottom:auto;
               border-style:solid; /*Selecting solid made the border appear*/
               border-left:1px;
               border-right:1px;
               border-top:0px;
               border-bottom:1px;
               border-color:#000000;
               text-align:left;
               background-color: blue;
               
}

        #inner {
               width:97%;
               height:80%;
               margin-left:auto;
               margin-right:auto;
               margin-top:auto;
               margin-bottom:auto;
               border-style:solid; /*Selecting solid made the border appear*/
               border-left:1px;
               border-right:1px;
               border-top:0px;
               border-bottom:1px;
               border-color:#000000;
               text-align:center;
               background-color: white;
               overflow-y: scroll;

        }

        #addcomment {
          position:absolute;
          width:97%;
          height:18%;
          margin-left:1px;
          margin-right:auto;
          margin-top:auto;
          margin-bottom:1px;
          border-style:solid; /*Selecting solid made the border appear*/
          border-left:1px;
          border-right:1px;
          border-top:1px;
          border-bottom:1px;
          border-color:#000000;
          text-align:center;
          background-color: blue;
        }

        #outer {
         
               width:25%; /* The width is fixed by pixels */
               height:500px; /* The height is fixed by pixels*/
               color:#fff;
               background-color:grey;
               border-style:solid; /*Selecting solid made the border appear*/
               border-left:1px;
               border-right:1px;
               border-top:0px;
               border-bottom:0px;
               border-color:#000000;
               text-align:center;

        }
        
        

      </style>

</head>
<body>





  <div id="main">
    <div id=”content” style = "width: 70%;
        height: 50%;     margin-left:1%;
            margin-right:auto;
            margin-top:1%;
            margin-bottom:auto display: inline-block;">

</div> <!-- content -->

  <div id="outer">
My Friends
      <div id="inner">
      
        
<c:if test="${myFriends.size() > 0}">
<c:forEach items="${myFriends}" var="friend">
 
 <p ><a href="GetData?data=getFriendGalleries&friendName=<c:out value="${friend}"/>"><c:out value="${friend}"/></a> </p>
</c:forEach>
</c:if>


      </div id="addcomment">
      
      <div id = "addForm">
           <form action="/form-processing-script"  method="post" >
       
          <label id = "textinput" for="addcomment">Add Friend  </label>
          <input type="text" name="comment"  />
      
       
<input id="button" type="submit" class="submit" value="Add" />

    </div>
    </div>

</div> <!-- main -->
</body>
</html>
