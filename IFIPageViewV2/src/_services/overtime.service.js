import { authHeader } from '../_helpers/auth-header.js';
import { systemConstants } from '../_constants/system.constants.js';


export const overtimeService = {
    getOvertimeByPage,
    getCategory,
    getEmployeeOvertimeByManager,
    saveOvertime,
    editOvertime,
    deleteOvertime,
    performApprove,
    performUnApprove,
    getOvertimeBySearchNeedApprove,
    getOvertimeBySearch,
    getOvertimeBySearchNeedApprove,
    getApprovedOvertimeLogByManager,
    getDisApprovedOvertimeLogByManager,
    getOvertimeBySearchApproved,
    getOvertimeBySearchDisApproved
};

function getOvertimeByPage(page,pageSize,sorted,bool,status){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    if(bool===undefined) bool='';
    let url = systemConstants.API_URL + "/api/overtimes/employee?page="+page+"&pageSize="+pageSize+"&is_approved="+bool+"&status="+status+'&sortedColumn='+sorted.id+ '&desc='+sorted.desc;
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
    let url = systemConstants.API_URL+"/api/overtimes/category";
    return fetch(url,requestOptions).then(handleResponse);
}
function getEmployeeOvertimeByManager(page, pageSize, sorted){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    let url = systemConstants.API_URL + "/api/overtimes/manager?page="+page+"&pageSize="+pageSize+'&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    // if(sorted && sorted.id && sorted.desc){
    //     url += '&sortedColumn='+sorted.id; + '&desc='+sorted.desc;
    // }
    
    return fetch(url,requestOptions).then(handleResponse);
}
function getApprovedOvertimeLogByManager(page, pageSize,sorted){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    let url = systemConstants.API_URL + "/api/overtimes/manager/approved?page="+page+"&pageSize="+pageSize+'&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    
    return fetch(url,requestOptions).then(handleResponse);
}

function getDisApprovedOvertimeLogByManager(page, pageSize,sorted){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    let url = systemConstants.API_URL + "/api/overtimes/manager/disapproved?page="+page+"&pageSize="+pageSize+'&sortedColumn='+sorted.id+ '&desc='+sorted.desc;
    
    return fetch(url,requestOptions).then(handleResponse);
}

function getOvertimeBySearchNeedApprove(page,pageSize,sorted,bool,search){
    let contentType = { 'Content-Type': 'application/json' };
    const requestOptions = {
        method: "POST",
        headers:  {...contentType,...authHeader()},
        body: JSON.stringify(search)
    };
    if(bool===undefined) bool ='';
    let url = systemConstants.API_URL + "/api/overtimes/manager/search/needapprove?page="+page+"&pageSize="+pageSize+"&is_approved="+bool+'&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    // if(sorted && sorted.id && sorted.desc){
    //     url += '&sortedColumn='+sorted.id; + '&desc='+sorted.desc;
    // }
    
    return fetch(url,requestOptions).then(handleResponse);
}
function getOvertimeBySearchApproved(page,pageSize,sorted,bool,search){
    let contentType = { 'Content-Type': 'application/json' };
    const requestOptions = {
        method: "POST",
        headers:  {...contentType,...authHeader()},
        body: JSON.stringify(search)
    };
    if(bool===undefined) bool ='';
    let url = systemConstants.API_URL + "/api/overtimes/manager/search/approved?page="+page+"&pageSize="+pageSize+"&is_approved="+bool+'&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    // if(sorted && sorted.id && sorted.desc){
    //     url += '&sortedColumn='+sorted.id; + '&desc='+sorted.desc;
    // }
    
    return fetch(url,requestOptions).then(handleResponse);
}
function getOvertimeBySearchDisApproved(page,pageSize,sorted,bool,search){
    let contentType = { 'Content-Type': 'application/json' };
    const requestOptions = {
        method: "POST",
        headers:  {...contentType,...authHeader()},
        body: JSON.stringify(search)
    };
    if(bool===undefined) bool ='';
    let url = systemConstants.API_URL + "/api/overtimes/manager/search/disapproved?page="+page+"&pageSize="+pageSize+"&is_approved="+bool+'&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    // if(sorted && sorted.id && sorted.desc){
    //     url += '&sortedColumn='+sorted.id; + '&desc='+sorted.desc;
    // }
    
    return fetch(url,requestOptions).then(handleResponse);
}
function getOvertimeBySearch(page,pageSize,sorted,bool,status,search){
    let contentType = { 'Content-Type': 'application/json' };
    const requestOptions = {
        method: "POST",
        headers:  {...contentType,...authHeader()},
        body: JSON.stringify(search)
    };
    if(bool===undefined) bool ='';
    let url = systemConstants.API_URL + "/api/overtimes/employee/search?page="+page+"&pageSize="+pageSize+"&is_approved="+bool+"&status="+status+'&sortedColumn='+sorted.id + '&desc='+sorted.desc;
    // if(sorted && sorted.id && sorted.desc){
    //     url += '&sortedColumn='+sorted.id; + '&desc='+sorted.desc;
    // }
    
    return fetch(url,requestOptions).then(handleResponse);
}
function saveOvertime(overtimes) {
    let dateClone = overtimes.overtimeDate;
    let contentType = { 'Content-Type': 'application/json' };
    const requestOptions = {
        method: 'POST',
        headers: {...contentType,...authHeader()},
        body: JSON.stringify(overtimes)
    };

   return fetch(systemConstants.API_URL + '/api/overtimes', requestOptions)
    .then(response => {
        if (!response.ok) {
            return Promise.reject(response.statusText);
        }
        return response.json();

    });
}

function editOvertime(overtimes){
    let contentType = { 'Content-Type': 'application/json' };
    const requestOptions = {
        method: 'PUT',
        headers: {...contentType,...authHeader()},
        body: JSON.stringify(overtimes)
    };
    return fetch(systemConstants.API_URL+'/api/overtimes',requestOptions).then(handleResponse);

}
function deleteOvertime(id){
    const requestOptions = {
        method: "DELETE",
        headers: authHeader()
    };
    return fetch(systemConstants.API_URL+"/api/overtimes/"+id,requestOptions).then(handleResponse);
}


function performApprove(id){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    return fetch(systemConstants.API_URL+"/api/overtimes/approve?overtime_id="+id,requestOptions).then(handleResponse);
}

function performUnApprove(id){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    return fetch(systemConstants.API_URL+"/api/overtimes/disapprove?overtime_id="+id,requestOptions).then(handleResponse);
}
function handleResponse(response) {
    if (!response.ok) {
        return Promise.reject(response.statusText);
    }
    return response.json();
}
