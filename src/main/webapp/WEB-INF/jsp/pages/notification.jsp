<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <th>Tip</th>
        <th>Kimdən</th>
        <th>Kimə</th>
        <th>Başlıq</th>
        <th>Mesaj</th>
        <th>Açıqlama</th>
        <th>Göndərildi</th>
        <th>Status</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list}" varStatus="loop">
        <tr data="<c:out value="${utl:toJson(t)}" />">
            <td>${loop.index + 1}</td>
            <td><c:out value="${t.id}" /></td>
            <td><c:out value="${t.type.name}" /></td>
            <td><c:out value="${t.from}" /></td>
            <td><c:out value="${t.to}" /></td>
            <td><c:out value="${t.subject}" /></td>
            <td><c:out value="${t.message}" /></td>
            <td><c:out value="${t.description}" /></td>
            <td><fmt:formatDate value = "${t.sendingDate}" pattern = "dd.MM.yyyy hh:mm:ss" /></td>
            <td class="text-center">
                <c:choose>
                    <c:when test="${t.sent}">
                        <span class="kt-font-bold kt-font-success"><i class="la la-check"></i></span>
                    </c:when>
                    <c:otherwise>
                        <span class="kt-spinner kt-spinner--md kt-spinner--danger" style="margin-left: -20px;"></span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td nowrap class="text-center">
                <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                <c:choose>
                    <c:when test="${delete.status}">
                        <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.to}" /><br/><c:out value="${t.subject}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                            <i class="<c:out value="${delete.object.icon}"/>"></i>
                        </a>
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
    </c:when>
    <c:otherwise>
        <div class="row">
                                <div class="col-md-12 text-center" style="letter-spacing: 10px;">
                                    Məlumat yoxdur
                                </div>
                            </div>
    </c:otherwise>
</c:choose>
                </div>
            </div>
        </div>

    </div>
</div>


<div class="modal fade" id="modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/admin/notification" cssClass="form-group">
                    <form:input type="hidden" name="id" path="id"/>
                    <form:input type="hidden" name="active" path="active" value="1"/>
                    <div class="form-group">
                        <form:radiobuttons items="${notifications}" path="type" itemLabel="name" itemValue="id"/>
                        <form:errors path="type" cssClass="control-label alert alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="to">Kimə?</form:label>
                        <form:input path="to" cssClass="form-control" placeholder="Email və ya telefon nömrəsi" />
                        <form:errors path="to" cssClass="alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="subject">Başlıq</form:label>
                        <form:input path="subject" cssClass="form-control" placeholder="Başlığı daxil edin" />
                        <form:errors path="subject" cssClass="alert alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="message">Mesaj mətni</form:label>
                        <form:textarea path="message" cssClass="form-control" placeholder="Mesajı daxil edin"></form:textarea>
                        <form:errors path="message" cssClass="alert alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" placeholder="Açıqlama daxil edin"></form:textarea>
                        <form:errors path="description" cssClass="alert alert-danger"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

