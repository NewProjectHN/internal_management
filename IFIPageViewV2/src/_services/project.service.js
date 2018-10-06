// import { authHeader } from '../_helpers/auth-header.js';
// import { systemConstants } from '../_constants/system.constants.js';
//
// export const ProjectService = {
//     getProjectByPage,
//     getProjectBySearch,
//     addProject,
//     updateProject,
//     deleteProject,
//     performAddEmployee
//
// };
// function getProjectByPage(group_id,page, pageSize){
//     const requestOptions = {
//         method: "GET",
//         headers: authHeader()
//     };
//     let url = systemConstants.API_URL + "/api/projects/getProjectsOfGroup?group_id="+group_id+"&page="+page+"&pageSize="+pageSize;
//
//     return fetch(url,requestOptions).then(handleResponse);
// }
// function getProjectBySearch(search,page,pageSize){
//     let contentType = { 'Content-Type': 'application/json' };
//     const requestOptions = {
//         method: "POST",
//         headers:  {...contentType,...authHeader()},
//         body: JSON.stringify(search)
//     };
//     let url = systemConstants.API_URL + "/api/projects/findProjectNameLike?projectName="+search+"&page="+page+"&pageSize="+pageSize;
//
//     return fetch(url,requestOptions).then(handleResponse);
// }
// function handleResponse(response) {
//     if (!response.ok) {
//         return Promise.reject(response.statusText);
//     }
//     return response.json();
// }
//
// function addProject(project) {
//     // let dateClone = leave.overtimeDate;
//     let contentType = { 'Content-Type': 'application/json' };
//     const requestOptions = {
//         method: 'POST',
//         headers: {...contentType,...authHeader()},
//         body: JSON.stringify(project)
//     };
//
//     return fetch(systemConstants.API_URL + '/api/projects/create', requestOptions)
//         .then(response => {
//             if (!response.ok) {
//                 return Promise.reject(response.statusText);
//             }
//             return response.json();
//
//         });
// }
//
// function updateProject(project) {
//     // let dateClone = leave.overtimeDate;
//     let contentType = { 'Content-Type': 'application/json' };
//     const requestOptions = {
//         method: 'PUT',
//         headers: {...contentType,...authHeader()},
//         body: JSON.stringify(project)
//     };
//
//     return fetch(systemConstants.API_URL + '/api/projects/updateProject', requestOptions)
//         .then(response => {
//             if (!response.ok) {
//                 return Promise.reject(response.statusText);
//             }
//             return response.json();
//
//         });
// }
//
// function deleteProject(id){
//     const requestOptions = {
//         method: "DELETE",
//         headers: authHeader()
//     };
//     return fetch(systemConstants.API_URL+"/api/projects/deleteProject?project_id="+id,requestOptions).then(handleResponse);
// }
// function performAddEmployee(employee){
//     const requestOptions = {
//         method: "POST",
//         headers: authHeader(),
//         body: JSON.stringify(employee)
//     };
//     return fetch(systemConstants.API_URL+"/api/projects/AddMemberToProject",requestOptions).then(handleResponse);
// }
