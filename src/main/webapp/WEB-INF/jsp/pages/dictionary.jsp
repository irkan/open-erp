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
                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Ad</th>
                                    <th>Tipi</th>
                                    <th>Atribut#1</th>
                                    <th>Atribut#2</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                     <tr data="<c:out value="${utl:toJson(t)}" />">
                                        <td><c:out value="${t.id}" /></td>
                                        <th><c:out value="${t.name}" /></th>
                                        <th><c:out value="${t.dictionaryType.name}" /></th>
                                        <th><c:out value="${t.attr1}" /></th>
                                        <td><c:out value="${t.attr2}" /></td>
                                         <td nowrap class="text-center">
                                             <c:if test="${view.status}">
                                                 <a href="javascript:view($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${view.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                     <i class="<c:out value="${view.object.icon}"/>"></i>
                                                 </a>
                                             </c:if>
                                             <c:if test="${edit.status}">
                                                 <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                     <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                 </a>
                                             </c:if>
                                             <c:if test="${delete.status}">
                                                 <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yeni sorğu yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/admin/dictionary" cssClass="form-group">
                    <form:input type="hidden" path="id"/>
                    <form:input type="hidden" path="active" value="1"/>
                    <div class="form-group">
                        <form:label path="name">Ad</form:label>
                        <form:input path="name" cssClass="form-control" placeholder="Adı daxil edin"/>
                        <form:errors path="name" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="attr1">Atribut#1</form:label>
                        <form:input path="attr1" cssClass="form-control" placeholder="Atributu daxil edin" />
                        <form:errors path="attr1" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="attr2">Atribut#2</form:label>
                        <form:input path="attr2" cssClass="form-control" placeholder="Atributu daxil edin" />
                        <form:errors path="attr2" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="dictionaryType">Tip</form:label>
                        <form:select  path="dictionaryType" cssClass="custom-select form-control">
                            <form:option value=""></form:option>
                            <form:options items="${dictionary_types}" itemLabel="name" itemValue="id" />
                        </form:select>
                        <form:errors path="dictionaryType" cssClass="alert-danger control-label"/>
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
    $('#group_table').DataTable({
        <c:if test="${export.status}">
        dom: 'B<"clear">lfrtip',
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ],
        </c:if>
        responsive: true,
        pageLength: 100,
        order: [[2, 'asc']],
        drawCallback: function(settings) {
            var api = this.api();
            var rows = api.rows({page: 'current'}).nodes();
            var last = null;

            api.column(2, {page: 'current'}).data().each(function(group, i) {
                if (last !== group) {
                    $(rows).eq(i).before(
                        '<tr class="group"><td colspan="30">' + group + '</td></tr>'
                    );
                    last = group;
                }
            });
        },
        columnDefs: [
            {
                targets: [2],
                visible: false
            }
        ]
    });

    $('#group_table tbody').on('dblclick', 'tr', function () {
        <c:if test="${edit.status}">
        edit($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${edit.object.name}" />');
        </c:if>
        <c:if test="${!edit.status and view.status}">
        view($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />');
        </c:if>
    });

    $( "#form" ).validate({
        rules: {
            name: {
                required: true
            },
            attr1: {
                required: true
            },
            dictionaryType: {
                required: true
            },
        },
        invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
            swal.close();
        },
    });
</script>


