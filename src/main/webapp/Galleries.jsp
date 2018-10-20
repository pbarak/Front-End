<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import= "java.util.ArrayList"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>


div.gal {
   margin: 5px;
   border: 1px solid #ccc;
   width: 180px;
   height: 240px;
   float: left;
}

div.gal:hover {
   border: 1px solid #777;
}

div.gal img {
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




<c:if test="${galleryEntries.size() > 0}">
<c:forEach items="${galleryEntries}" var="gallery">
 <div class="gal">
<a href="<c:url value="/Web/GetData">
<c:param name="data" value="mygallery" />
<c:param name="galleryid" value="${gallery.key}" />
</c:url>">
   <img src="folder.png" alt="Folder" width="400" height="200"></a>
    <div class="desc"><c:out value="${gallery.value}"/></div>
  </div> 
</c:forEach><br /><br />
</c:if>
		


</body>
</html>
