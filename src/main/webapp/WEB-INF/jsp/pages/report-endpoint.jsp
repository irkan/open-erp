<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 20.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="random" class="java.util.Random" scope="application"/>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <c:set var="filter" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'filter')}"/>
    <c:if test="${filter.status}">
        <div class="accordion  accordion-toggle-arrow mb-2" id="accordionFilter">
            <div class="card" style="border-radius: 4px;">
                <div class="card-header">
                    <div class="card-title w-100" data-toggle="collapse" data-target="#filterContent" aria-expanded="true" aria-controls="collapseOne4">
                        <div class="row w-100">
                            <div class="col-3">
                                <i class="<c:out value="${filter.object.icon}"/>"></i>
                            </div>
                            <div class="col-6 text-center" style="letter-spacing: 10px;">
                                <c:out value="${filter.object.name}"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="filterContent" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionFilter">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-7">
                                <form:form modelAttribute="filter" id="filter-line" method="post" action="/report/api/report-endpoint-line">
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="string1">Endpoint</form:label>
                                                <form:select path="string1" cssClass="custom-select form-control">
                                                    <form:option value=" ">Bütün endpointlər üzrə</form:option>
                                                    <form:options items="${endpoints}" itemLabel="url" itemValue="reportCondition"/>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="integer1">Baxış periodu</form:label>
                                                <form:select path="integer1" cssClass="custom-select form-control">
                                                    <form:option value="0">Son 1 həftəlik</form:option>
                                                    <form:option value="1">Son 1 aylıq</form:option>
                                                    <form:option value="2">Son 12 aylıq</form:option>
                                                    <form:option value="3">Son 10 illik</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-3 text-right">
                                            <div class="form-group pt-3">
                                                <button class="btn btn-warning btn-elevate btn-icon-sm btn-block mt-2">
                                                    <i class="la la-search"></i> Hesabla
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                            <div class="col-md-5">
                                <form:form modelAttribute="filter" id="filter-pie" method="post" action="/report/api/report-endpoint-pie">
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="string1">Endpoint</form:label>
                                                <form:select path="string1" cssClass="custom-select form-control">
                                                    <form:option value=" ">Bütün endpointlər üzrə</form:option>
                                                    <form:options items="${endpoints}" itemLabel="url" itemValue="reportCondition"/>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="string2">Tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="string2" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="string2" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="string3">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="string3" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="string3" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3 text-right">
                                            <div class="form-group pt-3">
                                                <button class="btn btn-warning btn-elevate btn-icon-sm btn-block mt-2">
                                                    <i class="la la-search"></i> Hesabla
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <div class="row">
        <div class="col-md-8">
            <div class="kt-portlet kt-portlet--mobile">
                <div id="chart-line" class="pl-1 pr-3 pt-2"></div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="kt-portlet kt-portlet--mobile">
                <div id="chart-pie" class="pl-1 pr-3 pt-2 d-flex justify-content-center"></div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
<script>
    $(function(){
        var lineOptions = {
            series: [{
                name: 'Down Time',
                type: 'line',
                data: []
            }],
            chart: {
                height: $( window ).height()-4*$("#accordionFilter").height()>350?$( window ).height()-3*$("#accordionFilter").height():350,
                type: 'line',
                stacked: false
            },
            dataLabels: {
                enabled: false
            },
            stroke: {
                width: [5]
            },
            title: {
                text: 'XY - down time analizi',
                align: 'left',
                offsetX: 50
            },
            xaxis: {
                categories: [],
            },
            yaxis: [
                {
                    seriesName: 'Down Time',
                    axisTicks: {
                        show: true,
                    },
                    axisBorder: {
                        show: true,
                        color: '#196dfe'
                    },
                    labels: {
                        style: {
                            colors: '#196dfe',
                        },
                    },
                    title: {
                        text: "Down Time (dəqiqə ilə)",
                        style: {
                            color: '#196dfe',
                        }
                    },
                    tooltip: {
                        enabled: true
                    }
                },
            ],
            tooltip: {
                fixed: {
                    enabled: true,
                    position: 'topLeft', // topRight, topLeft, bottomRight, bottomLeft
                    offsetY: 30,
                    offsetX: 60
                },
            },
            legend: {
                horizontalAlign: 'left',
                offsetX: 20
            }
        };

        var chartLine = new ApexCharts(document.querySelector("#chart-line"), lineOptions);
        chartLine.render();

        $("#filter-line").ajaxForm({
            beforeSubmit: function(){
                swal.showLoading();

                lineOptions.xaxis.categories = [];
                lineOptions.series[0].data = [];
            },
            success: function(data){

                $.each(jQuery.parseJSON(data.string1), function(key, val){
                    lineOptions.xaxis.categories.push(val.ZAXIS);
                    lineOptions.series[0].data.push(round(val.YAXIS, 0));
                });

                chartLine.updateOptions(lineOptions);

                swal.close();
            },
            complete: function(){
                swal.close();
            },
            error: function(){
                swal.close();
            }
        });

        var totalSum=0;
        var pieoptions = {
            series: [0, 0],
            chart: {
                height: $( window ).height()-4*$("#accordionFilter").height()>350?$( window ).height()-3*$("#accordionFilter").height():350,
                type: 'donut',
                stacked: false
            },
            plotOptions: {
                pie: {
                    donut: {
                        labels: {
                            show: true,
                            name: {
                                fontSize: '22px',
                                fontWeight: 'bold',
                                color: '#088fd7'
                            },
                            value: {
                                fontSize: '16px',
                                fontWeight: 'bold',
                                color: '#FF325B'
                            },
                            total: {
                                show: true,
                                label: 'DowTime',
                                formatter: function () {
                                    return totalSum + ' dəqiqə';
                                }
                            }
                        }
                    }
                }
            },
            labels: ['UpTime', 'DownTime'],
            legend: {
                position: 'bottom'
            },
            responsive: [
                {
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200
                        }, legend: {
                            position: 'bottom'
                        }
                    }
                }
            ]
        };

        var piechart = new ApexCharts(document.querySelector("#chart-pie"), pieoptions);
        piechart.render();

        $("#filter-pie").ajaxForm({
            beforeSubmit: function(){
                swal.showLoading();

                pieoptions.labels = [];
                pieoptions.series = [];
                totalSum = 0;
            },
            success: function(data){
                $.each(jQuery.parseJSON(data.string1), function(key, val){
                    pieoptions.labels.push("UpTime");
                    pieoptions.series.push(round(val.UP_TIME, 0));
                    pieoptions.labels.push("DownTime");
                    pieoptions.series.push(round(val.DOWN_TIME, 0));
                    totalSum = round(val.DOWN_TIME, 0);
                });
                piechart.updateOptions(pieoptions);

                swal.close();
            },
            complete: function(){
                swal.close();
            },
            error: function(){
                swal.close();
            }
        });

    });
</script>