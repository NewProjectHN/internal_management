import {projectConstants} from '../_constants/project.constants';

export function project(state = {},action){
    switch(action.type){
        case projectConstants.GETAL_REQUEST:
            return {
                loading: true
            };
        case projectConstants.GETAL_SUCCESS:
            return{
                loading: false,
                projectList: action.projectBean.data,
                pages: action.projectBean.pages
            }
        case projectConstants.GETAL_FAILURE:
            return{
                loading: false,
                error: action.error
            }
        case projectConstants.SEARCH_PROJECT_REQUEST:
            return{
                loading: true
            };
        case projectConstants.SEARCH_PROJECT_SUCCESS:
            return{
                loading: false,
                searchList: action.projectSearch.data,
                searchPages: action.projectSearch.pages
        }
        case projectConstants.SEARCH_PROJECT_FAILURE:
            return{
                loading: false,
                error: action.error
            }

        case projectConstants.SAVE_PROJECT_SUCCESS:
            let projectOldList=state.projectList;
            const projectNewList=[];
            
            projectNewList.push(action.project.data);
            if(projectOldList !=undefined){
                for(var i=0;i<projectOldList.length;i++)
                    projectNewList.push(projectOldList[i]);
            }
            
           
           
            
            return{
                projectList: projectNewList,
                pages: state.pages
            };
            
        case projectConstants.EDIT_PROJECT_SUCCESS:
            let updateList = state.projectList;
            const update = [];
            if(updateList){
                for(var i=0;i<updateList.length;i++){
                    
                    if(updateList[i].project_id == action.project.data.project_id)
                        update.push(action.project.data);
                    else
                        update.push(updateList[i]);
                    
                }
            }
            return{
                projectList: update,
                pages: state.pages
            }




        case projectConstants.DELETE_PROJECT_SUCCESS:
            let projectList = state.projectList;
            let projectDelete = [];
      
            for (var i = 0 ;i < projectList.length;i++) {
              if(projectList[i].projectList != action.id){
                projectDelete.push(projectList[i]);
              }
            }
           // state.leaveList = leaveDelete;
            return {
                projectList: projectDelete,
                pages: state.pages
            };
        case projectConstants.ADD_LIST_EMPLOYEE_REQUEST:
            return{
                loading:true
            };

        case projectConstants.ADD_LIST_EMPLOYEE_SUCCESS:
            let employeeListNotIn = state.employeeListNotIn;
            let employeeList=state.employeeList;
            let employeeNotApp =[];
            let employeeApp = [];
            employeeApp.push(action.employeeNotInBean.data1);
            if(employeeList!=undefined){
                for(var i=0;i<employeeList.length;i++)
                    employeeApp.push(employeeList[i]);
            }
            if(employeeListNotIn!=undefined){
                for(var i=0;i<employeeListNotIn.length;i++)
                    employeeNotApp.push(employeeListNotIn[i]);
            }
            
            return {
                pages:state.pages,
                employeeListNotIn: employeeNotApp,
                employeeList: employeeApp
            };
         default:
            return state;
        }
    }