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
<%@ taglib prefix="ua" uri="/WEB-INF/tld/UserAccess.tld"%>
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
                                <table class="table table-striped- table-bordered table-hover table-checkable" id="module_operation_table">
                                    <thead>
                                        <tr>
                                            <td style="width: 15px;"></td>
                                            <td style="width: 25%"></td>
                                            <td></td>
                                            <c:forEach var="t" items="${operations}" varStatus="loop">
                                                <td class="text-center" style="width: ${75/operations.size()}%">
                                                    <c:out value="${t.name}" />
                                                </td>
                                            </c:forEach>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="m" items="${modules}" varStatus="loop">
                                        <c:choose>
                                        <c:when test="${not empty m.module}">
                                        <tr>
                                            <td>${loop.index + 1}</td>
                                            <td class="text-right"><c:out value="${m.name}" /></td>
                                            <td><c:out value="${m.module.name}" /></td>
                                            <c:forEach var="o" items="${operations}" varStatus="loop">
                                                <td class="text-center">
                                                    <c:set var="moduleOperationId" value="${ua:checkAccess(list, m.id, o.id)}"/>
                                                    <c:choose>
                                                        <c:when test="${moduleOperationId ne 0}">
                                                            <form:checkbox path="id" value="${moduleOperationId}"/>
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                            </c:forEach>
                                        </tr>
                                        </c:when>
                                        </c:choose>
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

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>


