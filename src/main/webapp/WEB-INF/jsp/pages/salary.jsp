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
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="alert alert-light alert-elevate" role="alert">
        <div class="alert-icon"><i class="flaticon-warning kt-font-brand"></i></div>
        <div class="alert-text">
            Əmək haqqı hesablamalı ayda bir dəfə olmaqla hər ayın sonu həyata keçirilməlidir! Hesablama əməliyyatının ağırlığını nəzərə alaraq bildirirk ki, göstərilmiş tarix üzrə hesablanmış əmək haqqı üçün təkrar sorğu göndərildikdə, nəticələr tarixçədən təqdim ediləcəkdir!
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__head kt-portlet__head--lg">
                    <div class="kt-portlet__head-title" style="width: 100%">
                        <form:form modelAttribute="form" id="filter-form" method="post" action="/payroll/salary/filter">
                            <div class="row">
                                <div class="col-sm-3 offset-sm-2">
                                    <div class="form-group">
                                        <form:label path="branch">&nbsp;</form:label>
                                        <form:select  path="branch" cssClass="custom-select form-control">
                                            <form:options items="${branches}" itemLabel="name" itemValue="id" />
                                        </form:select>
                                        <form:errors path="branch" cssClass="control-label alert-danger" />
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="form-group">
                                        <form:label path="month">&nbsp;</form:label>
                                        <form:select  path="month" cssClass="custom-select form-control">
                                            <form:options items="${months}" itemLabel="name" itemValue="attr2" />
                                        </form:select>
                                        <form:errors path="month" cssClass="control-label alert-danger" />
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="form-group">
                                        <form:label path="year">&nbsp;</form:label>
                                        <form:select  path="year" cssClass="custom-select form-control">
                                            <form:options items="${years}"/>
                                        </form:select>
                                        <form:errors path="year" cssClass="control-label alert-danger" />
                                    </div>
                                    <%--<div class="form-group">
                                        <label>&nbsp;</label>
                                        <select  name="year" class="custom-select form-control">
                                            <c:forEach var="t" items="${years}" varStatus="loop">
                                                <option value="<c:out value="${t}"/>"><c:out value="${t}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>--%>
                                </div>
                                <div class="col-sm-2 text-center">
                                    <label>&nbsp;</label>
                                    <div class="form-group">
                                        <a href="#" onclick="submit($('#filter-form'))" class="btn btn-brand btn-elevate btn-icon-sm">
                                            <i class="la la-filter"></i>
                                            Filter
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
                <div class="kt-portlet__body">

                    <c:choose>
                        <c:when test="${not empty list}">
                            <table class="table table-striped- table-bordered table-hover table-checkable"
                                   id="kt_table_1">
                                <thead>
                                <tr>
                                    <th rowspan="2">№</th>
                                    <th colspan="3" class="text-center" style="letter-spacing: 4px;">Əməkdaş</th>
                                    <th colspan="<c:out value="${days_in_month}"/>" class="text-center" style="letter-spacing: 4px;">Ayın günləri</th>
                                </tr>
                                <tr>
                                    <th>Struktur</th>
                                    <th>Ad Soyad Ata adı</th>
                                    <th>Vəzifə</th>
                                    <c:forEach var = "i" begin = "1" end = "${days_in_month}">
                                        <th class="bg-light"><c:out value = "${i}"/></th>
                                    </c:forEach>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.organization.name}"/></td>
                                        <th><c:out value="${t.person.firstName}"/> <c:out value="${t.person.lastName}"/>
                                            <c:out value="${t.person.fatherName}"/></th>
                                        <td><c:out value="${t.position.name}"/></td>
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
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <%--<form:form modelAttribute="form" id="form" method="post" action="/admin/dictionary-type"
                           cssClass="form-group">
                    <form:input type="hidden" name="id" path="id"/>
                    <form:input type="hidden" name="active" path="active" value="1"/>
                    <div class="form-group">
                        <form:label path="name">Ad</form:label>
                        <form:input path="name" cssClass="form-control" placeholder="Adı daxil edin"/>
                        <form:errors path="name" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="attr1">Atribut#1</form:label>
                        <form:input path="attr1" cssClass="form-control" placeholder="Atributu daxil edin"/>
                        <form:errors path="attr1" cssClass="alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="attr2">Atribut#2</form:label>
                        <form:input path="attr2" cssClass="form-control" placeholder="Atributu daxil edin"/>
                        <form:errors path="attr2" cssClass="alert alert-danger"/>
                    </div>
                </form:form>--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>


