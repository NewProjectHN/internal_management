import {  employeeConstants } from '../_constants/employee.constants.js';
import { EmployeeService } from '../_services/employee.service';


export const employeeActions = {
   getListEmployeeInProjectDoNotAllocated,
    getListEmployeeNotInProject,
    getEmployeeById,
    getEmployees,
    deleteLeave
};
function deleteLeave(employee_id){
    return dispatch => {
        dispatch(request(employee_id));
        EmployeeService.deleteEmployee(employee_id).then(
            employee_id => dispatch(success(employee_id)),
            employee_id => dispatch(failure(employee_id))
        );
    };
    function request(employee_id) {return  {type: employeeConstants.DELETE_EMPLOYEE_REQUEST,employee_id:employee_id}}
    function success(employee_id) { return {type: employeeConstants.DELETE_EMPLOYEE_SUCCESS,employee_id:employee_id}}
    function failure(employee_id) { return {type: employeeConstants.DELETE_EMPLOYEE_ERROR, employee_id:employee_id}}
}
function getEmployees(){
    return dispatch => {
        dispatch(request());
        EmployeeService.getEmployees().then(
            empList => dispatch(success(empList)),
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: employeeConstants.GETALL_REQUEST}}
    function success(empList) { return {type: employeeConstants.GETALL_SUCCESS,empList}}
    function failure(error) { return {type: employeeConstants.GETALL_FAILURE, error}}
}
function getListEmployeeInProjectDoNotAllocated(project_id,page,pageSize){
    return dispatch => {
        dispatch(request());
        EmployeeService.getListEmployeeInProjectDoNotAllocated(project_id,page,pageSize).then(
            employeeBean =>dispatch(success(employeeBean)),
            error=>dispatch(failure(error))
        );
    };
        function request() {return {type: employeeConstants.GETALL_REQUEST_EMPLOYEE_IN_PROJECT}}
        function success(employeeBean) {return{type: employeeConstants.GETALL_SUCCESS_EMPLOYEE_IN_PROJECT, employeeBean}}
        function failure(error) {return {type: employeeConstants.GETALL_FAILURE_EMPLOYEE_IN_PROJECT, error}}
}

function getEmployeeById(employee_id){
    return dispatch => {
        dispatch(request());
        EmployeeService.getEmployeeById(employee_id).then(
            employeeBean =>dispatch(success(employeeBean)),
            error=>dispatch(failure(error))
        );
    };
        function request() {return {type: employeeConstants.GETALL_REQUEST_EMPLOYEE_IN_PROJECT}}
        function success(employeeBean) {return{type: employeeConstants.GETALL_SUCCESS_EMPLOYEE_IN_PROJECT, employeeBean}}
        function failure(error) {return {type: employeeConstants.GETALL_FAILURE_EMPLOYEE_IN_PROJECT, error}}
}

function getListEmployeeNotInProject(project_id,page,pageSize){
    return dispatch => {
        dispatch(request());
        EmployeeService.getListEmployeeNotInProject(project_id,page,pageSize).then(
            employeeNotInBean =>{
                dispatch(success(employeeNotInBean));
            },
            error=>dispatch(failure(error))

        );
    };
        function request() {return {type: employeeConstants.GETALL_REQUEST_EMPLOYEE_NOTIN_PROJECT}}
        function success(employeeNotInBean) {return{type: employeeConstants.GETALL_SUCCESS_EMPLOYEE_NOTIN_PROJECT, employeeNotInBean}}
        function failure(error) {return {type: employeeConstants.GETALL_FAILURE_EMPLOYEE_NOTIN_PROJECT, error}}
}
