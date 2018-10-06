// Convert về dạng đích
export default function convertData(data, ArrayHeader, ArrayColumn, IndexId, sortColumn) {
  const tableData = {
    headerCol : [...ArrayHeader],//Dữ liệu cho header của table
    columns:test(ArrayColumn),//Lọc dữ liệu columns
    rows:Object.values(data),//Dữ liệu cho body
    indexId: IndexId, // Id phục vụ cho sửa xóa
    sortColumn: [...sortColumn]
  }
  console.log(tableData.sortColumn);
  return tableData;
}

// convertData(dữ liệu để hiển thị ra table, Tên các cột của header, các dữ liệu trong body, id phục vụ cho sửa xóa)

function test (item){
  const a = [];
  for(var i=0;i<item.length;i++)
    a.push(item[i]);
  return a;
}