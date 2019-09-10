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

<c:choose>
    <c:when test="${not empty list}">
<table class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1">
    <thead>
    <tr>
        <th>№</th>
        <th>ID</th>
        <th>Ad</th>
        <th>Atribut#1</th>
        <th>Atribut#2</th>
        <th>Aktiv</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td><c:out value="${t.id}" /> <%--${uj:toJson(t)}--%></td>
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
    </c:forEach>
    </tbody>
</table>
    </c:when>
    <c:otherwise>
        Məlumat yoxdur
    </c:otherwise>
</c:choose>
                </div>
            </div>
        </div>

    </div>
</div>


<div class="modal fade" id="modal-create-new" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yeni sorğu tipi yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" method="post" action="/admin/dictionary-type" cssClass="form-group">
                    <form:input type="hidden" name="id" path="id"/>
                    <form:input type="hidden" name="active" path="active" value="1"/>
                    <div class="form-group">
                        <form:label path="name">Ad</form:label>
                        <form:input path="name" cssClass="form-control" placeholder="Adı daxil edin"/>
                        <form:errors path="name" cssClass="alert alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="attr1">Atribut#1</form:label>
                        <form:input path="attr1" cssClass="form-control" placeholder="Atributu daxil edin" />
                        <form:errors path="attr1" cssClass="alert alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="attr2">Atribut#2</form:label>
                        <form:input path="attr2" cssClass="form-control" placeholder="Atributu daxil edin" />
                        <form:errors path="attr2" cssClass="alert alert-danger"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="$('#form').submit()">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>


