<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>


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


<c:if test="${images.size() > 0}">
<c:forEach items="${images}" var="image">
 <div class="gallery">
 
<a href="<c:url value="/Web/GetData">
<c:param name="data" value="image" />
<c:param name="imageURL" value="${image.imageFile}" />
<c:param name="image" value="${image.imageId}" />
</c:url>">
   <img src="${image.imageFile}"  width="300" height="200"></a>
  </div> 
</c:forEach><br /><br />
</c:if>
		


</body>
</html>