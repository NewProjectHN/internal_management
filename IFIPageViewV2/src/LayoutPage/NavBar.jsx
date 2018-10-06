import React from 'react';
import MenuComponent from '../_components/MenuComponent';


class NarBar extends React.Component {
    constructor(props) {
      super(props);
      
    }
  
    
    render() {
        const data = [
            {
                label: "Home",
                id: 1,
                icon: "test",
                collapsed: true,
                slug: "home",
                children: [
                    {
                        label: "Home Item 1",
                        slug: "home/item-1",
                        id: 2,
                        icon: "test"
                    },
                    {
                        label: "Home Item 2",
                        slug: "home/item-2",
                        id: 3,
                        icon: "test"
                    }
                ]
            },
            {
                label: "About",
                slug: "about",
                id: 2,
                icon: ""
            }
        ];
      return (
        <div style={{ width: 200 }}>
            <MenuComponent data={data} />
        </div>
      );
    }
  }
  
  export default NarBar ;