import './App.css'
import DepartmentComponent from './components/DepartmentComponent'
import EmployeeComponent from './components/EmployeeComponent'
import FooterComponent from './components/FooterComponent'
import HeaderComponent from './components/HeaderComponent'
import ListDepartmentComponent from './components/ListDepartmentComponent'
import ListEmployeeComponent from './components/ListEmployeeComponent'
import { BrowserRouter, Route, Routes } from 'react-router-dom'

function App() {
  return (
    <>
      <BrowserRouter>
        <HeaderComponent />
        <Routes>
          {/* http://localhost:3000 */}
          <Route path="/" element={<ListEmployeeComponent />} />
          {/* http://localhost:3000/employees */}
          <Route path="/employees" element={<ListEmployeeComponent />} />
          {/* http://localhost:3000/add-employee */}
          <Route path="/add-employee" element={<EmployeeComponent />} />
          {/* http://localhost:3000/edit-employee */}
          <Route path="/edit-employee/:id" element={<EmployeeComponent />} />
          {/* http://localhost:3000/departments */}
          <Route path="/departments" element={<ListDepartmentComponent />} />
          {/* http://localhost:3000/add-department */}
          <Route path="/add-department" element={<DepartmentComponent />} />
          {/* http://localhost:3000/edit-department */}
          <Route
            path="/edit-department/:id"
            element={<DepartmentComponent />}
          />
        </Routes>
        <FooterComponent />
      </BrowserRouter>
    </>
  )
}

export default App
