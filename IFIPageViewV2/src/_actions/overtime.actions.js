import { overtimeConstants } from '../_constants/overtime.constants.js';
import { overtimeService } from '../_services/overtime.service.js';
import { alertActions } from './alert.actions.js';
import {overtimeManagerConstants} from '../_constants/overtimeManager.constants'

export const overtimeActions = {
    getOvertimeByPage,
    getOvertimeInit,
    getEmployeeOvertimeByManager,
    saveOvertime,
    editOvertime,
    deleteOvertime,
    performApprove,
    performUnApprove,
    getOvertimeBySearch,
    getOvertimeBySearchNeedApprove,
    getApprovedOvertimeLogByManager,
    getOvertimeCategory,
    getDisApprovedOvertimeLogByManager,
    getOvertimeBySearchApproved,
    getOvertimeBySearchDisApproved
};

function getOvertimeByPage(page,pageSize,sorted,bool,status){
    return dispatch => {
        dispatch(request());
        overtimeService.getOvertimeByPage(page,pageSize,sorted,bool,status).then(
            overtimeBean => dispatch(success(overtimeBean)),
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: overtimeConstants.GETALL_REQUEST}}
    function success(overtimeBean) { return {type: overtimeConstants.GETALL_SUCCESS,overtimeBean}}
    function failure(error) { return {type: overtimeConstants.GETALL_FAILURE, error}}
}

function getOvertimeInit(page,pageSize,sorted,bool,status){
    return function(dispatch){
        return overtimeService.getCategory().then(
            overtimeCategory => {
                dispatch(success(overtimeCategory));
                dispatch(getOvertimeByPage(page,pageSize,sorted,bool,status));
            },
            error => dispatch(failure(error))
        );
    };
    function success(overtimeCategory) {return {type: overtimeConstants.GET_OVERTIME_CATEGORY,overtimeCategory}}
    function failure(err) { return {type: overtimeConstants.GET_OVERTIME_CATEGORY_FAILURE, err}}
}
function getOvertimeCategory(page,pageSize){
    return function(dispatch){
        return overtimeService.getCategory().then(
            overtimeCategory=>{
                dispatch(success(overtimeCategory));
            },
            error =>dispatch(failure)
        )
    }
    function success(overtimeCategory) {return {type: overtimeConstants.GET_OVERTIME_CATEGORY,overtimeCategory}}
    function failure(err) { return {type: overtimeConstants.GET_OVERTIME_CATEGORY_FAILURE, err}}
}
function getEmployeeOvertimeByManager(page, pageSize,sorted){
    return dispatch => {
        dispatch(request());
        overtimeService.getEmployeeOvertimeByManager(page,pageSize,sorted).then(
            overtimeEmp => {dispatch(success(overtimeEmp))},
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: overtimeManagerConstants.GETALL_REQUEST}}
    function success(overtimeEmp) { return {type: overtimeManagerConstants.GETALL_SUCCESS,overtimeEmp}}
    function failure(error) { return {type: overtimeManagerConstants.GETALL_FAILURE, error}}
}

function getApprovedOvertimeLogByManager(page, pageSize,sorted){
    return dispatch => {
        dispatch(request());
        overtimeService.getApprovedOvertimeLogByManager(page,pageSize,sorted).then(
            overtimeEmp => {dispatch(success(overtimeEmp))},
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: overtimeManagerConstants.GETALL_APPROVED_REQUEST}}
    function success(overtimeEmp) { return {type: overtimeManagerConstants.GETALL_APPROVED_SUCCESS,overtimeEmp}}
    function failure(error) { return {type: overtimeManagerConstants.GETALL_APPROVED_FAILURE, error}}
}

function getDisApprovedOvertimeLogByManager(page, pageSize,sorted){
    return dispatch => {
        dispatch(request());
        overtimeService.getDisApprovedOvertimeLogByManager(page,pageSize,sorted).then(
            overtimeEmp => {dispatch(success(overtimeEmp))},
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: overtimeManagerConstants.GETALL_DISAPPROVED_REQUEST}}
    function success(overtimeEmp) { return {type: overtimeManagerConstants.GETALL_DISAPPROVED_SUCCESS,overtimeEmp}}
    function failure(error) { return {type: overtimeManagerConstants.GETALL_DISAPPROVED_FAILURE, error}}
}
function saveOvertime(overtime) {
    return dispatch => {
        //dispatch(request());

        overtimeService.saveOvertime(overtime)
            .then(
                overtime =>{ 
                    dispatch(success(overtime));
                    dispatch(alertActions.success('Save successfully'));
                    },
                //error => dispatch(failure(error))
            );
    };

    //function request() { return { type: overtimeConstants.GETALL_REQUEST } }
    function success(overtime) {
      return { type: overtimeConstants.SAVE_OVERTIME_SUCCESS, overtime }
    }
    //function failure(error) { return { type: overtimeConstants.GETALL_FAILURE, error } }
}

function editOvertime(overtime){
    return dispatch => {
        overtimeService.editOvertime(overtime)
            .then(
                overtime =>{
                    dispatch(success(overtime));
                    dispatch(alertActions.success('Edit successfully'));
                },
            );
    };
    function success(overtime){
        return {type: overtimeConstants.EDIT_OVERTIME_SUCCESS, overtime}
    }

}
function deleteOvertime(id){
    return dispatch => {
        dispatch(request());
        overtimeService.deleteOvertime(id).then(
            () => {
                dispatch(success(id));
                dispatch(alertActions.success('Delete successfully'));
            },
            error => dispatch(failure(error))
        );
    }

    function request(){return {type: overtimeConstants.DELETE_OVERTIME_REQUEST}}
    function success(id){return {type: overtimeConstants.DELETE_OVERTIME_SUCCESS,id}}
    function failure(error){return {type: overtimeConstants.DELETE_OVERTIME_FAILURE, error}}
}

function performApprove(id){
    return dispatch => {
        // dispatch(request());
        overtimeService.performApprove(id).then(
            overtimeEmpApprove => {dispatch(success(overtimeEmpApprove));
                dispatch(alertActions.success('Approved Successfully'))},
            error => dispatch(failure(error))
        )
    }
    function request() {return  {type: overtimeManagerConstants.APPROVE_REQUEST}}
    function success(overtimeEmpApprove) { return {type: overtimeManagerConstants.APPROVE_SUCCESS,overtimeEmpApprove}}
    function failure(error) { return {type: overtimeManagerConstants.APPROVE_FAILURE, error}}
}

function performUnApprove(id){
    return dispatch => {
        // dispatch(request());
        overtimeService.performUnApprove(id).then(
            overtimeEmpUnApprove => {dispatch(success(overtimeEmpUnApprove));
                dispatch(alertActions.success('UnApproved Successfully'))},
            error => dispatch(failure(error))
        )
    }
    function request() {return  {type: overtimeManagerConstants.UNAPPROVE_REQUEST}}
    function success(overtimeEmpUnApprove) { return {type: overtimeManagerConstants.UNAPPROVE_SUCCESS,overtimeEmpUnApprove}}
    function failure(error) { return {type: overtimeManagerConstants.UNAPPROVE_FAILURE, error}}
}


function getOvertimeBySearch(page,pageSize,sorted,bool,status,search){
    return dispatch => {
        dispatch(request());
        overtimeService.getOvertimeBySearch(page,pageSize,sorted,bool,status,search).then(
            overtimeSearch => dispatch(success(overtimeSearch)),
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: overtimeConstants.SEARCH_OVERTIME_REQUEST}}
    function success(overtimeSearch) { return {type: overtimeConstants.SEARCH_OVERTIME_SUCCESS,overtimeSearch}}
    function failure(error) { return {type: overtimeConstants.SEARCH_OVERTIME_FAILURE, error}}
}

function getOvertimeBySearchNeedApprove(page,pageSize,sorted,bool,search){
    return dispatch => {
        dispatch(request());
        overtimeService.getOvertimeBySearchNeedApprove(page,pageSize,sorted,bool,search).then(
            empSearch => dispatch(success(empSearch)),
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: overtimeManagerConstants.SEARCH_OVERTIME_REQUEST}}
    function success(empSearch) { return {type: overtimeManagerConstants.SEARCH_OVERTIME_SUCCESS,empSearch}}
    function failure(error) { return {type: overtimeManagerConstants.SEARCH_OVERTIME_FAILURE, error}}
}

function getOvertimeBySearchApproved(page,pageSize,sorted,bool,search){
    return dispatch => {
        dispatch(request());
        overtimeService.getOvertimeBySearchApproved(page,pageSize,sorted,bool,search).then(
            empSearch => dispatch(success(empSearch)),
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: overtimeManagerConstants.SEARCH_OVERTIME_REQUEST}}
    function success(empSearch) { return {type: overtimeManagerConstants.SEARCH_OVERTIME_SUCCESS,empSearch}}
    function failure(error) { return {type: overtimeManagerConstants.SEARCH_OVERTIME_FAILURE, error}}
}

function getOvertimeBySearchDisApproved(page,pageSize,sorted,bool,search){
    return dispatch => {
        dispatch(request());
        overtimeService.getOvertimeBySearchDisApproved(page,pageSize,sorted,bool,search).then(
            empSearch => dispatch(success(empSearch)),
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: overtimeManagerConstants.SEARCH_OVERTIME_REQUEST}}
    function success(empSearch) { return {type: overtimeManagerConstants.SEARCH_OVERTIME_SUCCESS,empSearch}}
    function failure(error) { return {type: overtimeManagerConstants.SEARCH_OVERTIME_FAILURE, error}}
}