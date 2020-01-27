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
    <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
    <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
<table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
    <thead>
    <tr>
        <th>№</th>
        <th>ID</th>
        <th>İstifadəçi adı</th>
        <th>Şifrə</th>
        <th>Yaradılmışdır</th>
        <th>Açıqlama</th>
        <th>Status</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list}" varStatus="loop">
        <tr data="<c:out value="${utl:toJson(t)}" />">
            <td>${loop.index + 1}</td>
            <td><c:out value="${t.id}" /></td>
            <td><c:out value="${t.username}" /></td>
            <td><c:out value="${t.password}" /></td>
            <td><fmt:formatDate value = "${t.createdDate}" pattern = "dd.MM.yyyy" /></td>
            <td><c:out value="${t.description}" /></td>
            <td>
                <c:choose>
                    <c:when test="${t.active}">
                        <span class="kt-badge kt-badge--success kt-badge--inline kt-badge--pill">Aktivdir</span>
                    </c:when>
                    <c:otherwise>
                        <span class="kt-badge kt-badge--danger kt-badge--inline kt-badge--pill">Aktiv deyil</span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td nowrap class="text-center">
                <c:if test="${edit.status}">
                    <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                        <i class="<c:out value="${edit.object.icon}"/>"></i>
                    </a>
                </c:if>
                <c:if test="${delete.status}">
                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.username}" /><br/><c:out value="${t.description}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                        <i class="<c:out value="${delete.object.icon}"/>"></i>
                    </a>
                </c:if>
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
                <form:form modelAttribute="form" id="form" method="post" action="/admin/web-service-authenticator" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="active"/>
                    <div class="form-group">
                        <form:label path="username">İstifadəçi adı</form:label>
                        <form:input path="username" cssClass="form-control" placeholder="Adı daxil edin"/>
                        <form:errors path="username" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="password">Şifrə</form:label>
                        <form:input path="password" cssClass="form-control" placeholder="Açarı daxil edin" />
                        <form:errors path="password" cssClass="alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" placeholder="Açıqlama daxil edin" />
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

<script>
    <c:if test="${edit.status}">
    $('#datatable tbody').on('dblclick', 'tr', function () {
        edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
    });
    </c:if>

    $("#datatable").DataTable({
        responsive: true,
        lengthMenu: [10, 25, 50, 75, 100, 200, 1000],
        pageLength: 100,
        order: [[1, 'desc']],
        columnDefs: [
            {
                targets: 0,
                width: '25px',
                className: 'dt-center',
                orderable: false
            },
        ],
    });

    $( "#form" ).validate({
        rules: {
            username: {
                required: true,
                minlength: 5
            },
            password: {
                required: true,
                minlength: 6
            }
        },
        invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
            swal.close();
        },
    })
</script>


