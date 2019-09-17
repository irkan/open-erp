<%--
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
<%@ taglib prefix="ua" uri="/WEB-INF/tld/UserAccess.tld"%>
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
                                    <th>Ad</th>
                                    <th>Atribut#1</th>
                                    <th>Atribut#2</th>
                                    <th>Tipi</th>
                                    <th>Aktiv</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.name}" /></td>
                                        <td><c:out value="${t.attr1}" /></td>
                                        <td><c:out value="${t.attr2}" /></td>
                                        <td><c:out value="${t.dictionaryType.name}" /></td>
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
                                        <td nowrap class="text-center">
                                            <c:set var="view" value="${ua:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                                            <c:choose>
                                                <c:when test="${view.status}">
                                                    <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                        <i class="la <c:out value="${view.object.icon.name}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="edit" value="${ua:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                                            <c:choose>
                                                <c:when test="${edit.status}">
                                                    <a href="javascript:edit($('#form'), '<c:out value="${uj:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="la <c:out value="${edit.object.icon.name}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="delete" value="${ua:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                                            <c:choose>
                                                <c:when test="${delete.status}">
                                                    <a href="javascript:deleteData(<c:out value="${t.id}" />);" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                        <i class="la <c:out value="${delete.object.icon.name}"/>"></i>
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
                            Məlumat yoxdur
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
                        <td><form:input type="text" path="name" cssClass="form-control" placeholder="Adı daxil edin"/>
                    </div>
                    <div class="form-group">
                        <form:label path="attr1">Atribut#1</form:label>
                        <form:input type="text" path="attr1" cssClass="form-control" placeholder="Atributu daxil edin" />
                    </div>
                    <div class="form-group">
                        <form:label path="attr2">Atribut#2</form:label>
                        <form:input type="text" path="attr2" cssClass="form-control" placeholder="Atributu daxil edin" />
                    </div>
                    <div class="form-group">
                        <form:label path="dictionaryType">Tip</form:label>
                        <form:select  path="dictionaryType" cssClass="custom-select form-control">
                            <form:options items="${dictionary_types}" itemLabel="name" itemValue="id" />
                        </form:select>
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




