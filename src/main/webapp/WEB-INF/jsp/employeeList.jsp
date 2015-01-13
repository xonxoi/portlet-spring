<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" isELIgnored="false" %>

<fmt:setBundle basename="content.Language-niteco"/>

<style>
table {
    border-collapse: collapse;
    width: 30%;
}

table thead{
    background-color: #AABBCC;
}

table td, th{
    padding-left: 10px;
    border-collapse: collapse;
    border: solid 1px black;
}

input[type=text]{
    width: 90%;
}
</style>
<script type="text/javascript">
function showForm(){
    document.getElementById("tblEmployee").style.display = "none";
    document.getElementById("frmAdd").style.display = "block";
}

function showList(){
    document.getElementById("frmAdd").style.display = "none";
    document.getElementById("tblEmployee").style.display = "block";
}

function validate(){
    var sal = document.getElementById("inputSal").value;
    
    if(!isNaN(sal) && sal != ""){
        document.getElementById("frmAdd").submit();
    }
    else{
        alert("Salary must be an integer");
    }
}
</script>

<c:out value="${label}"/>
<br></br>
<fmt:message key="label.display" />
<br></br>
<table id="tblEmployee" style="display:block;">
    <colgroup>
        <col width="13%">
        <col width="27%">
        <col width="25%">
        <col width="20%">
        <col width="15%">
    </colgroup>
    <thead>
        <tr>
            <td>Id</td>
            <td>Employee Name</td>
	        <td>Department</td>
	        <td>Position</td>
	        <td>Salary</td>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="elm" items="${employees}">
        <tr>
            <td><c:out value="${elm.employeeId}"/></td>
            <td><c:out value="${elm.employeeName}"/></td>
            <td><c:out value="${elm.department}"/></td>
            <td><c:out value="${elm.position}"/></td>
            <td><c:out value="${elm.salary}"/></td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
        <tr height="30px !important">
            <td colspan="5">
                <input type="button" name="btnAdd" value="Add Employee" onclick="showForm()"/>
            </td>
        </tr>
    </tfoot>
</table>

<form id="frmAdd" action="${insertURL}" method="POST" style="display:none">
<table>
    <colgroup>
        <col width="30%">
        <col width="70%">
    </colgroup>
    <tr>
        <td>Employee Code</td>
        <td><input type="text" name="empCode"/></td>
    </tr>
    <tr>
        <td>Employee Name</td>
        <td><input type="text" name="empName"/></td>
    </tr>
    <tr>
        <td>Department</td>
        <td><input type="text" name="department"/></td>
    </tr>
    <tr>
        <td>Position</td>
        <td><input type="text" name="position"/></td>
    </tr>
    <tr>
        <td>Salary</td>
        <td><input type="text" id="inputSal" name="salary"/></td>
    </tr>
</table>
<input type="button" value="Submit" onclick="validate()"/>
<input type="button" value="Cancel" onclick="showList()"/>
</form>


