import { authHeader } from '../_helpers/auth-header.js';
import { systemConstants } from '../_constants/system.constants.js';


export const leaveService = {
    getLeaveByPage,
    deleteLeave,
    getCategory,
    saveLeave,
    editLeave,
    getEmployeeVacationByManager,
    performApprove,
    performUnApprove,
    getLeaveBySearch,
    getLeaveBySearchNeedApprove,
    getApprovedVacationLogByManager,
    getDisApprovedVacationLogByManager,
    getLeaveBySearchApproved,
    getLeaveBySearchDisApproved
};

function getLeaveByPage(page,pageSize,sorted,bool,status){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    if(bool===undefined) bool ='';
    let url = systemConstants.API_URL + "/api/vacations/employee?page="+page+"&pageSize="+pageSize+"&is_approved="+bool+"&status="+status+'&sortedColumn=&desc=';
    //let url = systemConstants.API_URL + "/api/vacations/employee?page="+page+"&pageSize="+pageSize+"&is_approved="+bool+"&status="+status+'&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    // if(sorted && sorted.id && sorted.desc){
    //     url += '&sortedColumn='+sorted.id; + '&desc='+sorted.desc;
    // }

    return fetch(url,requestOptions).then(handleResponse);

}

function getCategory(){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    let url = systemConstants.API_URL+"/api/vacations/category";
    return fetch(url,requestOptions).then(handleResponse);
}

function getLeaveBySearch(page,pageSize,sorted,bool,status,search){
    let contentType = { 'Content-Type': 'application/json' };
    const requestOptions = {
        method: "POST",
        headers:  {...contentType,...authHeader()},
        body: JSON.stringify(search)
    };
    if(bool===undefined) bool ='';
    let url = systemConstants.API_URL + "/api/vacations/searchv2?page="+page+"&pageSize="+pageSize+"&is_approved="+bool+"&status="+status+ '&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    // if(sorted){
    //     url += '&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    // }

    return fetch(url,requestOptions).then(handleResponse);
}

function saveLeave(leaves) {
    let dateClone = leaves.overtimeDate;
    let contentType = { 'Content-Type': 'application/json' };
    const requestOptions = {
        method: 'POST',
        headers: {...contentType,...authHeader()},
        body: JSON.stringify(leaves)
    };

   return fetch(systemConstants.API_URL + '/api/vacations', requestOptions).then(handleResponse);
}

function editLeave(leave){
    let contentType = { 'Content-Type': 'application/json' };
    const requestOptions = {
        method: 'PUT',
        headers: {...contentType,...authHeader()},
        body: JSON.stringify(leave)
    };
    return fetch(systemConstants.API_URL+'/api/vacations',requestOptions).then(handleResponse);

}
function deleteLeave(id){
    const requestOptions = {
        method: "DELETE",
        headers: authHeader()
    };
    return fetch(systemConstants.API_URL+"/api/vacations/"+id,requestOptions).then(handleResponse);
}

function getEmployeeVacationByManager(page, pageSize,sorted){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    let url = systemConstants.API_URL + "/api/vacations/manager?page="+page+"&pageSize="+pageSize+'&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    // if(sorted && sorted.id && sorted.desc){
    //     url += '&sortedColumn='+sorted.id; + '&desc='+sorted.desc;
    // }

    return fetch(url,requestOptions).then(handleResponse);
}
function getApprovedVacationLogByManager(page,pageSize,sorted){
    const requestOptions={
        method:"GET",
        headers:authHeader()
    };
    let url = systemConstants.API_URL+"/api/vacations/manager/approved?page="+page+"&pageSize="+pageSize+'&sortedColumn='+sorted.id + '&desc='+sorted.desc;

    return fetch(url,requestOptions).then(handleResponse);
}

function getDisApprovedVacationLogByManager(page, pageSize,sorted){
    const requestOptions={
        method:"GET",
        headers:authHeader()
    };
    let url = systemConstants.API_URL+"/api/vacations/manager/disapproved?page="+page+"&pageSize="+pageSize+'&sortedColumn='+sorted.id + '&desc='+sorted.desc;

    return fetch(url,requestOptions).then(handleResponse);
}
function getLeaveBySearchNeedApprove(page,pageSize,sorted,bool,search){
    let contentType = { 'Content-Type': 'application/json' };
    const requestOptions = {
        method: "POST",
        headers:  {...contentType,...authHeader()},
        body: JSON.stringify(search)
    };
    if(bool===undefined) bool ='';
    let url = systemConstants.API_URL + "/api/vacations/search/needapprove?page="+page+"&pageSize="+pageSize+"&is_approved="+bool+ '&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    // if(sorted && sorted.id && sorted.desc){
    //     url += '&sortedColumn='+sorted.id; + '&desc='+sorted.desc;
    // }

    return fetch(url,requestOptions).then(handleResponse);
}
function getLeaveBySearchApproved(page,pageSize,sorted,bool,search){
    let contentType = { 'Content-Type': 'application/json' };
    const requestOptions = {
        method: "POST",
        headers:  {...contentType,...authHeader()},
        body: JSON.stringify(search)
    };
    if(bool===undefined) bool ='';
    let url = systemConstants.API_URL + "/api/vacations/search/approved?page="+page+"&pageSize="+pageSize+"&is_approved="+bool+ '&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    // if(sorted && sorted.id && sorted.desc){
    //     url += '&sortedColumn='+sorted.id; + '&desc='+sorted.desc;
    // }

    return fetch(url,requestOptions).then(handleResponse);
}
function getLeaveBySearchDisApproved(page,pageSize,sorted,bool,search){
    let contentType = { 'Content-Type': 'application/json' };
    const requestOptions = {
        method: "POST",
        headers:  {...contentType,...authHeader()},
        body: JSON.stringify(search)
    };
    if(bool===undefined) bool ='';
    let url = systemConstants.API_URL + "/api/vacations/search/disapproved?page="+page+"&pageSize="+pageSize+"&is_approved="+bool+ '&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    // if(sorted && sorted.id && sorted.desc){
    //     url += '&sortedColumn='+sorted.id; + '&desc='+sorted.desc;
    // }

    return fetch(url,requestOptions).then(handleResponse);
}


function performApprove(id){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    return fetch(systemConstants.API_URL+"/api/vacations/approve?vacation_id="+id,requestOptions).then(handleResponse);
}

function performUnApprove(id){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    return fetch(systemConstants.API_URL+"/api/vacations/disapprove?vacation_id="+id,requestOptions).then(handleResponse);
}


function handleResponse(response) {
    if (!response.ok) {
        return Promise.reject(response.statusText);
    }
    return response.json();
}
