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

                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr style="width: 100%"/>
<c:choose>
    <c:when test="${not empty list}">
<table class="table table-striped- table-bordered table-hover table-checkable">
    <thead>
    <tr>
        <th>№</th>
        <c:forEach var="t" items="${operations}" varStatus="loop">
            <th><c:out value="${t.name}" /></th>
        </c:forEach>
    </tr>
    </thead>
    <tbody>
    <%--<c:forEach var="t" items="${list}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td><c:out value="${t.id}" /> &lt;%&ndash;${uj:toJson(t)}&ndash;%&gt;</td>
            <td><c:out value="${t.name}" /></td>
            <td><c:out value="${t.attr1}" /></td>
            <td><c:out value="${t.attr2}" /></td>
            <c:choose>
                <c:when test="${t.active}">
                    <td>
                        <span class="kt-badge kt-badge--success kt-badge--inline kt-badge--pill">Aktivdir</span>
                    </td>
                </c:when>
                <c:otherwise>
                    <td>
                        <span class="kt-badge kt-badge--danger kt-badge--inline kt-badge--pill">Aktiv deyil</span>
                    </td>
                </c:otherwise>
            </c:choose>
            <td nowrap>
                <span class="dropdown">
                    <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">
                      <i class="la la-ellipsis-h"></i>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right">
                        <a class="dropdown-item" href="#"><i class="la la-edit"></i> Edit Details</a>
                        <a class="dropdown-item" href="#"><i class="la la-leaf"></i> Update Status</a>
                        <a class="dropdown-item" href="#"><i class="la la-print"></i> Generate Report</a>
                    </div>
                </span>
                <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="View">
                    <i class="la la-edit"></i>
                </a>
            </td>
        </tr>
    </c:forEach>--%>
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


