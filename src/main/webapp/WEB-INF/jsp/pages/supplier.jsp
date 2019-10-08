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
                                    <th>Ad</th>
                                    <th>Açıqlama</th>
                                    <th>Əlaqəli şəxs</th>
                                    <th>Email</th>
                                    <th>Mobil nömrə</th>
                                    <th>Ofis nömrəsi</th>
                                    <th>Ünvan</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.name}" /></td>
                                        <td><c:out value="${t.description}" /></td>
                                        <td><c:out value="${t.person.firstName}" /> <c:out value="${t.person.firstName}" /> <c:out value="${t.person.lastName}" /> <c:out value="${t.person.fatherName}" /></td>
                                        <td><c:out value="${t.person.contact.email}" /></td>
                                        <td><c:out value="${t.person.contact.mobilePhone}" /></td>
                                        <td><c:out value="${t.person.contact.homePhone}" /></td>
                                        <td><c:out value="${t.person.contact.city.name}" />, <c:out value="${t.person.contact.address}" /></td>
                                        <td nowrap class="text-center">
                                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                                            <c:choose>
                                                <c:when test="${view.status}">
                                                    <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                        <i class="la <c:out value="${view.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                                            <c:choose>
                                                <c:when test="${edit.status}">
                                                    <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                                            <c:choose>
                                                <c:when test="${delete.status}">
                                                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/warehouse/supplier" cssClass="form-group">
                    <form:input path="id" type="hidden"/>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="name">Tədarükçü adı</form:label>
                                <form:input path="name" cssClass="form-control" placeholder="Adı daxil edin"/>
                                <form:errors path="name" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="description">Açıqlama</form:label>
                                <form:input path="description" cssClass="form-control" placeholder="Açıqlama daxil edin"/>
                                <form:errors path="description" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                    </div>
                    <hr style="width: 100%"/>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.firstName">Ad</form:label>
                                <form:input path="person.firstName" cssClass="form-control" placeholder="Adı daxil edin Məs: Səbuhi"/>
                                <form:errors path="person.firstName" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.lastName">Soyad</form:label>
                                <form:input path="person.lastName" cssClass="form-control" placeholder="Soyadı daxil edin Məs: Vəliyev"/>
                                <form:errors path="person.lastName" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.fatherName">Ata adı</form:label>
                                <form:input path="person.fatherName" cssClass="form-control" placeholder="Ata adını daxil edin"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.birthday">Doğum tarixi</form:label>
                                <div class="input-group date" >
                                    <form:input path="person.birthday" cssClass="form-control datepicker-element" date="date" placeholder="dd.MM.yyyy"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.birthday" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <fmt:parseNumber var = "grid" integerOnly = "true" type = "number" value = "${12/genders.size()}" />
                                <form:label path="person.gender">Cins</form:label><br/>
                                <form:radiobuttons items="${genders}" path="person.gender" itemLabel="name" itemValue="id"/>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.nationality">Milliyət</form:label>
                                <form:select  path="person.nationality" cssClass="custom-select form-control">
                                    <form:options items="${nationalities}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </div>
                        </div>
                    </div>
                    <hr style="width: 100%"/>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.email">Email</form:label>
                                <div class="input-group" >
                                    <form:input path="person.contact.email" cssClass="form-control" placeholder="example@example.com"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-at"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.contact.email" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.mobilePhone">Mobil nömrə</form:label>
                                <div class="input-group" >
                                    <form:input path="person.contact.mobilePhone" cssClass="form-control" placeholder="505505550"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.contact.mobilePhone" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.homePhone">Şəhər nömrəsi</form:label>
                                <div class="input-group" >
                                    <form:input path="person.contact.homePhone" cssClass="form-control" placeholder="124555050"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.contact.homePhone" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.city">Şəhər</form:label>
                                <form:select  path="person.contact.city" cssClass="custom-select form-control">
                                    <form:options items="${cities}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="form-group">
                                <form:label path="person.contact.address">Ünvan</form:label>
                                <div class="input-group" >
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-street-view"></i>
                                        </span>
                                    </div>
                                    <form:input path="person.contact.address" cssClass="form-control" placeholder="Küçə adı, ev nömrəsi və s."/>
                                </div>
                                <form:errors path="person.contact.address" cssClass="control-label alert-danger" />
                            </div>
                        </div>
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




