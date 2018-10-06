import { projectConstants } from '../_constants/project.constants.js';
import { ProjectService } from '../_services/project.service.js';
import { alertActions } from './alert.actions.js';


export const projectActions = {
    getProjectByPage,
    getProjectBySearch,
    saveProject,
    editProject,
    deleteProject,
    performAddEmployee
    
};

function getProjectByPage(group_id,page,pageSize){
    return dispatch => {
        dispatch(request());
        ProjectService.getProjectByPage(group_id,page,pageSize).then(
            projectBean =>dispatch(success(projectBean)),
            error=>dispatch(failure(error))
        );
    };
        function request() {return {type: projectConstants.GETAL_REQUEST}}
        function success(projectBean) {return{type:projectConstants.GETAL_SUCCESS, projectBean}}
        function failure(error) {return {type:projectConstants.GETAL_FAILURE, error}}
}

function getProjectBySearch(search,page,pageSize){
    return dispatch => {
        dispatch(request());
        ProjectService.getProjectBySearch(search,page,pageSize).then(
            projectSearch =>dispatch(success(projectSearch)),
            error=>dispatch(failure(error))
        );
    };
        function request() {return {type: projectConstants.SEARCH_PROJECT_REQUEST}}
        function success(projectSearch) {return{type:projectConstants.SEARCH_PROJECT_SUCCESS, projectSearch}}
        function failure(error) {return {type:projectConstants.SEARCH_PROJECT_FAILURE, error}}
}

function saveProject(project) {
    return dispatch => {
        //dispatch(request());

        ProjectService.addProject(project)
            .then(
                project =>{ 
                    dispatch(success(project));
                    dispatch(alertActions.success('Save successfully'));
                    },
                //error => dispatch(failure(error))
            );
    };

    //function request() { return { type: overtimeConstants.GETALL_REQUEST } }
    function success(project) {
      return { type: projectConstants.SAVE_PROJECT_SUCCESS, project }
    }
    //function failure(error) { return { type: overtimeConstants.GETALL_FAILURE, error } }
}

function editProject(project){
    return dispatch => {
        ProjectService.updateProject(project)
            .then(
                project =>{
                    dispatch(success(project));
                    dispatch(alertActions.success('Edit successfully'));
                },
            );
    };
    function success(project){
        return {type: projectConstants.EDIT_PROJECT_SUCCESS, project}
    }

}
function deleteProject(id){
    return dispatch => {
        dispatch(request());
        ProjectService.deleteProject(id).then(
            () => {
                dispatch(success(id));
                dispatch(alertActions.success('Delete successfully'));
                
            },
            error => dispatch(failure(error))
        );
    }

    function request(){return {type: projectConstants.DELETE_PROJECT_REQUEST}}
    function success(id){return {type: projectConstants.DELETE_PROJECT_SUCCESS,id}}
    function failure(error){return {type: projectConstants.DELETE_PROJECT_FAILURE, error}}
}
function performAddEmployee(employee){
    return dispatch =>{
        ProjectService.performAddEmployee(employee).then(
            addListEmployee => {dispatch(success(addListEmployee));
                dispatch(alertActions.success('Add List Employee Successfully'));
            },
             error =>dispatch(failure(error))   
        );
    }
    function request(){return {type: projectConstants.ADD_LIST_EMPLOYEE_REQUEST}}
    function success(addListEmployee) { return {type: projectConstants.ADD_LIST_EMPLOYEE_SUCCESS,addListEmployee}}
    function failure(error) { return {type: projectConstants.ADD_LIST_EMPLOYEE_FAILURE, error}}
}
