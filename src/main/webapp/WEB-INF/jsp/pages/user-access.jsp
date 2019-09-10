<%@ page import="az.sufilter.bpm.entity.DictionaryType" %><%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="uj" uri="/WEB-INF/tld/UtilJson.tld"%>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <form:form modelAttribute="form" method="post" action="/admin/user-access" cssClass="form-group">
                        <div class="row">
                            <div class="col-md-6 offset-md-3">
                                <div class="row">
                                    <div class="col-9">
                                        <form:select  path="user" cssClass="custom-select form-control">
                                            <form:options items="${users}" itemLabel="employee.person.fullName" itemValue="id" />
                                        </form:select>
                                    </div>
                                    <div class="col-3">
                                        <form:button class="btn btn-brand">Axtar</form:button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr style="width: 100%"/>
<c:choose>
    <c:when test="${not empty list}">
<table class="table table-striped- table-bordered table-hover table-checkable">
    <tbody>
    <c:forEach var="t" items="${list}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td><c:out value="${t.module.name}" /></td>
            <c:forEach var="p" items="${t.module.moduleOperations}" varStatus="loop">
                <td>
                    <form:label path="id">
                        <c:out value="${p.operation.name}" />
                        <form:checkbox path="id" value="${t.id}"/>
                    </form:label>

                </td>
            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>
    </c:when>
    <c:otherwise>
        Məlumat yoxdur
    </c:otherwise>
</c:choose>
                    </form:form>
                </div>
            </div>
        </div>

    </div>
</div>


