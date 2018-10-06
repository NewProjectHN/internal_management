
import { authHeader } from '../_helpers/auth-header.js';
import { systemConstants } from '../_constants/system.constants.js';


export const EmployeeService = {
    getListEmployeeInProjectDoNotAllocated,
    getListEmployeeNotInProject,
    getListSubEmployee,
    getEmployeeById,
    getEmployees,
    deleteEmployee

};
function deleteEmployee(employee_id) {
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    // TODO THIEU SERVICE XOA EMPLOYEE
    let url = systemConstants.API_URL + "/api/employees/deleteEmployee/?employee_id= "+employee_id;

    return fetch(url,requestOptions).then(handleResponse);
}
function getEmployees(){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    let url = systemConstants.API_URL + "/api/employees" ;
   
    
    return fetch(url,requestOptions).then(handleResponse);

}
function getListEmployeeInProjectDoNotAllocated(project_id,page, pageSize){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    let url = systemConstants.API_URL + "/api/employees/getListEmployeeInProject?project_id="+project_id+"&page="+page+"&pageSize="+pageSize;

    return fetch(url,requestOptions).then(handleResponse);
}

function getEmployeeById(employee_id){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    let url = systemConstants.API_URL + "/api/employees/getEmployeeById/?employee_id= "+employee_id;

    return fetch(url,requestOptions).then(handleResponse);
}

function getListEmployeeNotInProject(project_id,page, pageSize){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    let url = systemConstants.API_URL + "/api/employees/ManageMembersProject?project_id="+project_id+"&page="+page+"&pageSize="+pageSize;

    return fetch(url,requestOptions).then(handleResponse);
}

function getListSubEmployee(page, pageSize){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    let url = systemConstants.API_URL + "/api/employees/getListSubEmployee?page="+page+"&pageSize="+pageSize;

    return fetch(url,requestOptions).then(handleResponse);
}

function handleResponse(response) {
    if (!response.ok) {
        return Promise.reject(response.statusText);
    }
    return response.json();
}
