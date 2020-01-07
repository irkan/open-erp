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
    <c:set var="filter" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'filter')}"/>
    <c:if test="${filter.status}">
        <div class="accordion  accordion-toggle-arrow mb-2" id="accordionFilter">
            <div class="card" style="border-radius: 4px;">
                <div class="card-header">
                    <div class="card-title w-100" data-toggle="collapse" data-target="#filterContent" aria-expanded="true" aria-controls="collapseOne4">
                        <div class="row w-100">
                            <div class="col-6" style="font-size: 1.2rem; font-family: Arial, Helvetica, sans-serif">
                                <i class="<c:out value="${filter.object.icon}"/>"> <c:out value="${filter.object.name}"/></i>
                            </div>
                            <div class="col-6 text-right pr-md-4">
                                <a href="#" onclick="submit($('#filter'))" class="btn btn-danger btn-elevate btn-icon-sm" style="padding: 0.35rem 0.6rem">
                                    <i class="la la-trash"></i> Təmizlə
                                </a>
                                <a href="#" onclick="submit($('#filter'))" class="btn btn-warning btn-elevate btn-icon-sm" style="padding: 0.35rem 0.6rem">
                                    <i class="la la-search"></i> Axtar
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="filterContent" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionFilter">
                    <div class="card-body">
                        <form:form modelAttribute="filter-form" id="filter" method="post" action="/route/filter/admin/log">
                            <div class="row">
                                <div class="col-md-2">
                                    <div class="form-group">
                                        <label>Əməliyyat</label>
                                        <input type="text" name="conditions[0].value" placeholder="Daxil edin" class="form-control"/>
                                        <input type="hidden" name="conditions[0].type" value="string" />
                                        <input type="hidden" name="conditions[0].field" value="operation" />
                                        <input type="hidden" name="conditions[0].comparison" value="like" />
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <div class="form-group">
                                        <label>Cədvəl</label>
                                        <input type="text" name="conditions[2].value" placeholder="Daxil edin" class="form-control"/>
                                        <input type="hidden" name="conditions[2].type" value="string" />
                                        <input type="hidden" name="conditions[2].field" value="tableName" />
                                        <input type="hidden" name="conditions[2].comparison" value="like" />
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <label>Tip</label>
                                        <select name="conditions[3].value" class="form-control">
                                            <option></option>
                                            <option value="info">İnfo</option>
                                            <option value="error">Xəta</option>
                                        </select>
                                        <input type="hidden" name="conditions[3].type" value="string" />
                                        <input type="hidden" name="conditions[3].field" value="tableName" />
                                        <input type="hidden" name="conditions[3].comparison" value="like" />
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <div class="form-group">
                                        <label>İstifadəçi</label>
                                        <input type="text" name="conditions[4].value" placeholder="Daxil edin" class="form-control"/>
                                        <input type="hidden" name="conditions[4].type" value="string" />
                                        <input type="hidden" name="conditions[4].field" value="username" />
                                        <input type="hidden" name="conditions[4].comparison" value="like" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <label>Tarix</label>
                                    <div class="row">
                                        <div class="col-6">
                                            <div class="form-group">
                                                <div class="input-group date" >
                                                    <input type="text" name="conditions[5].value" class="form-control datetimepicker-element" date="date" placeholder="Tarixdən: dd.MM.yyyy hh:mm"/>
                                                    <div class="input-group-append">
                                                        <span class="input-group-text">
                                                            <i class="la la-calendar"></i>
                                                        </span>
                                                    </div>
                                                </div>
                                                <input type="hidden" name="conditions[5].type" value="date" />
                                                <input type="hidden" name="conditions[5].field" value="operationDate" />
                                                <input type="hidden" name="conditions[5].comparison" value="gt" />
                                            </div>
                                        </div>
                                        <div class="col-6">
                                            <div class="form-group">
                                                <div class="input-group date" >
                                                    <input type="text" name="conditions[6].value" class="form-control datetimepicker-element" date="date" placeholder="Tarixədək: dd.MM.yyyy hh:mm"/>
                                                    <div class="input-group-append">
                                                        <span class="input-group-text">
                                                            <i class="la la-calendar"></i>
                                                        </span>
                                                    </div>
                                                </div>
                                                <input type="hidden" name="conditions[6].type" value="date" />
                                                <input type="hidden" name="conditions[6].field" value="operationDate" />
                                                <input type="hidden" name="conditions[6].comparison" value="lt" />
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-md-2" style="padding-top: 30px;">
                                    <div class="form-group">
                                        <label class="kt-checkbox kt-checkbox--brand">
                                            <input type="checkbox" name="conditions[1].value" value="0"/> Silinmiş məlumat
                                            <span></span>
                                        </label>
                                        <input type="hidden" name="conditions[1].value" value="1" />
                                        <input type="hidden" name="conditions[1].type" value="numeric" />
                                        <input type="hidden" name="conditions[1].field" value="active" />
                                        <input type="hidden" name="conditions[1].comparison" value="eq" />
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>


    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">

<c:choose>
    <c:when test="${not empty list}">
    <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
    <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
    <table class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Tip</th>
        <th>Cədvəl</th>
        <th>Əməliyyat</th>
        <th>Yazı №</th>
        <th>Açıqlama</th>
        <th>Enkapsulasiya</th>
        <th>Tarix</th>
        <th>İstifadəçi</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list}" varStatus="loop">
        <tr data="<c:out value="${utl:toJson(t)}" />"
                <c:if test="${t.type eq 'error'}">
                    style="background-color: #ffeaf1 !important"
                </c:if>
        >
            <td><c:out value="${t.id}" /></td>
            <td><c:out value="${t.type}" /></td>
            <th><c:out value="${t.tableName}" /></th>
            <th><c:out value="${t.operation}" /></th>
            <td><c:out value="${t.rowId}" /></td>
            <td><c:out value="${t.description}" /></td>
            <td><c:out value="${t.encapsulate}" /></td>
            <td><fmt:formatDate value = "${t.operationDate}" pattern = "dd.MM.yyyy HH:mm:ss" /></td>
            <td><c:out value="${t.username}" /></td>
            <td nowrap class="text-center">
                <c:choose>
                    <c:when test="${edit.status}">
                        <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                            <i class="<c:out value="${edit.object.icon}"/>"></i>
                        </a>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${delete.status}">
                        <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.operation}" /><br/><c:out value="${t.description}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
                <form:form modelAttribute="form" id="form" method="post" action="/admin/log" cssClass="form-group">
                    <form:input type="hidden" name="id" path="id"/>
                    <form:input type="hidden" name="active" path="active" value="1"/>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="type">Tip</form:label>
                                <form:input path="type" cssClass="form-control" placeholder="Tipi daxil edin"/>
                                <form:errors path="type" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="tableName">Cədvəl</form:label>
                                <form:input path="tableName" cssClass="form-control" placeholder="Cədvəli daxil edin" />
                                <form:errors path="tableName" cssClass="alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="operation">Əməliyyat</form:label>
                                <form:input path="operation" cssClass="form-control" placeholder="Əməliyyatı daxil edin" />
                                <form:errors path="operation" cssClass="alert alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="rowId">Sətr №</form:label>
                                <form:input path="rowId" cssClass="form-control" placeholder="Sətr №-sini daxil edin" />
                                <form:errors path="rowId" cssClass="alert alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" placeholder="Açıqlamanı daxil edin" />
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
    $('#kt_table_1 tbody').on('dblclick', 'tr', function () {
        edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
    });
    </c:if>
</script>


