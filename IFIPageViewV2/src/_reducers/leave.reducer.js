import {leaveConstants} from '../_constants/leave.constants';
import { leaveManagerConstants } from '../_constants/leaveManager.constants';

export function leaves(state = {},action){
    switch(action.type){

        //My Leave
        case leaveConstants.GETALL_REQUEST:
            return {
                loading: true
            };
        case leaveConstants.GETALL_SUCCESS:
            return{
                loading: false,
                leaveList: action.leaveBean.data,
                pages: action.leaveBean.pages
            }
        case leaveConstants.GETALL_FAILURE:
            return{
                loading: false,
                error: action.error
            }
        case leaveConstants.SEARCH_LEAVE_REQUEST:
            return{
                loading: true
            };
        case leaveConstants.SEARCH_LEAVE_SUCCESS:
            return{
                loading: false,
                searchList: action.leaveSearch.data,
                searchPages: action.leaveSearch.pages
        }
        case leaveConstants.SEARCH_LEAVE_FAILURE:
            return{
                loading: false,
                error: action.error
            }

       

        case leaveConstants.SAVE_SUCCESS:
            let leaveOldList=state.leaveList;
            const leaveNewList=[];
            
            leaveNewList.push(action.leaves.data);
            if(leaveOldList !=undefined){
                for(var i=0;i<leaveOldList.length;i++)
                    leaveNewList.push(leaveOldList[i]);
            }
            
            return{
                leaveList: leaveNewList,
                pages: state.pages
            };
            
        case leaveConstants.EDIT_SUCCESS:
            let updateList = state.leaveList;
            const update = [];
            let searchList2=state.searchList;
            let searchPages2=state.searchPages;
            if(searchList2===undefined){
                    if(updateList){
                        for(var i=0;i<updateList.length;i++){
                            
                            if(updateList[i].vacation_id == action.leave.data.vacation_id)
                                update.push(action.leave.data);
                            else
                                update.push(updateList[i]);
                            
                        }
                    }
                    return{
                        leaveList: update,
                        pages: state.pages
                    }
                }
                else {
                    if(searchList2){
                        for(var i=0;i<searchList2.length;i++){
                            
                            if(searchList2[i].vacation_id == action.leave.data.vacation_id)
                                update.push(action.leave.data);
                            else
                                update.push(searchList2[i]);
                            
                        }
                    }
                    return{
                        leaveList: update,
                        pages: searchPages2
                    }
                }




        case leaveConstants.DELETE_LEAVE_SUCCESS:
            let leaveList = state.leaveList;
            let leaveDelete = [];
            let searchList=state.searchList;
            let searchPages=state.searchPages;
            if(searchList===undefined){
            for (var i = 0 ;i < leaveList.length;i++) {
              if(leaveList[i].vacation_id != action.id){
                leaveDelete.push(leaveList[i]);
              }
            }
        }
        else
        {for (var i = 0 ;i < searchList.length;i++) {
            if(searchList[i].vacation_id != action.id){
              leaveDelete.push(searchList[i]);
            }
          }
        }
           // state.leaveList = leaveDelete;
           if(searchPages===undefined){
            return {
                leaveList: leaveDelete,
                pages: state.pages
            };
        }
        else return {
            leaveList: leaveDelete,
                pages: searchPages
        }

            //My Approve leave
        case leaveManagerConstants.GETALL_REQUEST:
            return {
                loading: true
            };
        case leaveManagerConstants.GETALL_SUCCESS:
            return{
                loading: false,
                leaveEmp: action.leaveEmp.data,
                pages: action.leaveEmp.pages
            }
        case leaveManagerConstants.GETALL_FAILURE:
            return{
                loading: false,
                error: action.error
            }
            case leaveManagerConstants.GETALL_APPROVED_REQUEST:
            return{
                loading:true
            }
        case leaveManagerConstants.GETALL_APPROVED_SUCCESS:
            return{
                loading:false,
                leaveEmp:action.leaveEmp.data,
                pages:action.leaveEmp.pages
            }
        case leaveManagerConstants.GETALL_APPROVED_FAILURE:
            return{
                loading:false,
                error:action.error
            }
        ///////////////////////////////////////////////////////

        case leaveManagerConstants.GETALL_DISAPPROVED_REQUEST:
        return{
            loading:true
        }
    case leaveManagerConstants.GETALL_DISAPPROVED_SUCCESS:
        return{
            loading:false,
            leaveEmp:action.leaveEmp.data,
            pages:action.leaveEmp.pages
        }
    case leaveManagerConstants.GETALL_DISAPPROVED_FAILURE:
        return{
            loading:false,
            error:action.error
        }





        case leaveManagerConstants.APPROVE_REQUEST:
            return{
                loading:true
            };
        case leaveManagerConstants.APPROVE_SUCCESS:
            let leaveEmp = state.leaveEmp;
            let leaveApp = [];
            let searchEmpList=state.searchEmpList;
            let searchEmpPages=state.searchEmpPages;
            if(searchEmpList===undefined){
                    for(var i= 0 ; i < leaveEmp.length ; i++){
                        if(leaveEmp[i].vacation_id != action.leaveEmpApprove.data.vacation_id){
                            leaveApp.push(leaveEmp[i]);
                        }
                    }
                    return {
                        pages:state.pages,
                        leaveEmp: leaveApp
                    };
                }
                else {
                    for(var i= 0 ; i < searchEmpList.length ; i++){
                        if(searchEmpList[i].vacation_id != action.leaveEmpApprove.data.vacation_id){
                            leaveApp.push(searchEmpList[i]);
                        }
                    }
                    return {
                        pages: searchEmpPages,
                        leaveEmp: leaveApp
                    };
                }
        case leaveManagerConstants.UNAPPROVE_REQUEST:
            return{
                loading:true
            };
        case leaveManagerConstants.UNAPPROVE_SUCCESS:
            let leaveEmpUn = state.leaveEmp;
            let leaveAppUn = [];
            let searchEmpList2=state.searchEmpList;
            let searchEmpPages2=state.searchEmpPages;
              if(searchEmpList2===undefined){
                    for(var i= 0 ; i < leaveEmpUn.length ; i++){
                        if(leaveEmpUn[i].vacation_id != action.leaveEmpUnApprove.data.vacation_id){
                            leaveAppUn.push(leaveEmpUn[i]);
                        }
                    }
                    return {
                        pages:state.pages,
                        leaveEmp: leaveAppUn
                    };
                }
                else{
                    for(var i= 0 ; i < searchEmpList2.length ; i++){
                        if(searchEmpList2[i].vacation_id != action.leaveEmpUnApprove.data.vacation_id){
                            leaveAppUn.push(searchEmpList2[i]);
                        }
                    }
                    return {
                        pages:searchEmpPages2,
                        leaveEmp: leaveAppUn
                    };
                }
        case leaveManagerConstants.SEARCH_LEAVE_REQUEST:
            return{
                loading: true
            };
        case leaveManagerConstants.SEARCH_LEAVE_SUCCESS:
            return{
                loading: false,
                searchEmpList: action.empSearch.data,
                searchEmpPages: action.empSearch.pages
        }
        case leaveManagerConstants.SEARCH_LEAVE_FAILURE:
            return{
                loading: false,
                error: action.error
            }

        default:
            return state;
    }

}