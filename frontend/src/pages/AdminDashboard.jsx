import React, { useEffect, useState } from 'react';
import api from '../api';

export default function AdminDashboard() {
  const [employees, setEmployees] = useState([]);
  const [works, setWorks] = useState([]);
  const [title, setTitle] = useState('');
  const [desc, setDesc] = useState('');
  const [assignedTo, setAssignedTo] = useState('');

  // For create/edit employee
  const [newName, setNewName] = useState('');
  const [newEmail, setNewEmail] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [newRole, setNewRole] = useState('EMPLOYEE');
  const [editingId, setEditingId] = useState(null);

  const fetchAll = async () => {
    const e = await api.get('/admin/users');
    setEmployees(e.data);
    const w = await api.get('/admin/works');
    setWorks(w.data);
  };

  useEffect(() => {
    fetchAll();
  }, []);

  const assign = async () => {
    if (!assignedTo) return alert('Select employee');
    await api.post('/admin/works', {
      title,
      description: desc,
      assignedToId: Number(assignedTo)
    });
    setTitle('');
    setDesc('');
    setAssignedTo('');
    fetchAll();
  };

  const deleteEmployee = async (id) => {
    if (!window.confirm('Are you sure you want to delete this employee?')) return;
    try {
      await api.delete(`/admin/users/${id}`);
      setEmployees(employees.filter(emp => emp.id !== id));
    } catch (err) {
      console.error(err);
      alert('Failed to delete employee');
    }
  };

  const saveEmployee = async () => {
    try {
      if (editingId) {
        // Update existing
        await api.put(`/admin/users/${editingId}`, {
          name: newName,
          email: newEmail,
          role: newRole
        });
      } else {
        // Create new
        await api.post('/admin/users', {
          name: newName,
          email: newEmail,
          password: newPassword,
          role: newRole
        });
      }
      setNewName('');
      setNewEmail('');
      setNewPassword('');
      setNewRole('EMPLOYEE');
      setEditingId(null);
      fetchAll();
    } catch (err) {
      console.error(err);
      alert('Failed to save employee');
    }
  };

  const startEdit = (emp) => {
    setEditingId(emp.id);
    setNewName(emp.name);
    setNewEmail(emp.email);
    setNewRole(emp.role);
    setNewPassword('');
  };

  return (
    <div className="row">
      {/* Employees Section */}
      <div className="col-md-6">
        <div className="card p-3 mb-3">
          <h5>Employees</h5>
          {employees.map(emp => (
            <div
              key={emp.id}
              className="d-flex justify-content-between align-items-center border-bottom py-2"
            >
              <div>
                <strong>{emp.name}</strong>
                <div className="text-muted">{emp.email}</div>
              </div>
              <div className="d-flex align-items-center">
                <span className="me-3">{emp.role}</span>
                <button
                  className="btn btn-sm btn-warning me-2"
                  onClick={() => startEdit(emp)}
                >
                  Edit
                </button>
                <button
                  className="btn btn-sm btn-danger"
                  onClick={() => deleteEmployee(emp.id)}
                >
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>

        {/* Add/Edit Employee Form */}
        <div className="card p-3">
          <h5>{editingId ? 'Edit Employee' : 'Add New Employee'}</h5>
          <input
            className="form-control my-2"
            value={newName}
            onChange={e => setNewName(e.target.value)}
            placeholder="Name"
          />
          <input
            className="form-control my-2"
            value={newEmail}
            onChange={e => setNewEmail(e.target.value)}
            placeholder="Email"
          />
          {!editingId && (
            <input
              type="password"
              className="form-control my-2"
              value={newPassword}
              onChange={e => setNewPassword(e.target.value)}
              placeholder="Password"
            />
          )}
          <select
            className="form-select my-2"
            value={newRole}
            onChange={e => setNewRole(e.target.value)}
          >
            <option value="EMPLOYEE">EMPLOYEE</option>
            <option value="ADMIN">ADMIN</option>
          </select>
          <button className="btn btn-success" onClick={saveEmployee}>
            {editingId ? 'Update Employee' : 'Create Employee'}
          </button>
          {editingId && (
            <button
              className="btn btn-secondary ms-2"
              onClick={() => {
                setEditingId(null);
                setNewName('');
                setNewEmail('');
                setNewPassword('');
                setNewRole('EMPLOYEE');
              }}
            >
              Cancel
            </button>
          )}
        </div>
      </div>

      {/* Work Section */}
      <div className="col-md-6">
        <div className="card p-3 mb-3">
          <h5>Assign Work</h5>
          <input
            className="form-control my-2"
            value={title}
            onChange={e => setTitle(e.target.value)}
            placeholder="Title"
          />
          <textarea
            className="form-control my-2"
            value={desc}
            onChange={e => setDesc(e.target.value)}
            placeholder="Description"
          ></textarea>
          <select
            className="form-select my-2"
            value={assignedTo}
            onChange={e => setAssignedTo(e.target.value)}
          >
            <option value="">Select</option>
            {employees.map(emp => (
              <option key={emp.id} value={emp.id}>
                {emp.name}
              </option>
            ))}
          </select>
          <button className="btn btn-primary" onClick={assign}>
            Assign
          </button>
        </div>

        <div className="card p-3">
          <h5>All Works</h5>
          {works.map(w => (
            <div key={w.id} className="border p-2 mb-2">
              <div className="d-flex justify-content-between">
                <strong>{w.title}</strong>
                <span>{w.status}</span>
              </div>
              <div className="text-muted">{w.description}</div>
              <div className="text-muted mt-1">
                Assigned: {w.assignedToName}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}





// import React, { useEffect, useState } from 'react';
// import api from '../api';

// export default function AdminDashboard() {
//   const [employees, setEmployees] = useState([]);
//   const [works, setWorks] = useState([]);
//   const [title, setTitle] = useState('');
//   const [desc, setDesc] = useState('');
//   const [assignedTo, setAssignedTo] = useState('');

//   const fetchAll = async () => {
//     const e = await api.get('/admin/users');
//     setEmployees(e.data);
//     const w = await api.get('/admin/works');
//     setWorks(w.data);
//   };

//   useEffect(() => {
//     fetchAll();
//   }, []);

//   const assign = async () => {
//     if (!assignedTo) return alert('Select employee');
//     await api.post('/admin/works', {
//       title,
//       description: desc,
//       assignedToId: Number(assignedTo),
//     });
//     setTitle('');
//     setDesc('');
//     setAssignedTo('');
//     fetchAll();
//   };

//   const deleteEmployee=async(id)=>{
//     if(!window.confirm("are you sure want to delete this?"))return;
//     try{
//         await api.delete(`/admin/users/${id}`);
//         setEmployees(employees.filter(emp=>emp.id!==id));
//     }
//     catch(err){
//         console.log(err);
//         alert("failed")
//     }
//   }

//   return (
//     <div className="container mt-4">
//       <div className="row">
//         {/* Employees List */}
//         <div className="col-md-6">
//           <div className="card shadow-sm p-3 mb-4">
//             <h5 className="text-primary"><i className="fas fa-users"></i> Employees</h5>
//             {employees.map(emp => (
//               <div key={emp.id} className="d-flex justify-content-between border-bottom py-2">
//                 <div>
//                   <strong>{emp.name}</strong>
//                   <div className="text-muted">{emp.email}</div>
//                 </div>
//                 <span className="badge bg-secondary">{emp.role}</span>
//                 <button className='btn btn-sm btn-danger' onClick={()=>deleteEmployee(emp.id)}>delete</button>
//               </div>
//             ))}
//           </div>
//         </div>

//         {/* Assign Work */}
//         <div className="col-md-6">
//           <div className="card shadow-sm p-3 mb-4">
//             <h5 className="text-success"><i className="fas fa-tasks"></i> Assign Work</h5>
//             <input className="form-control my-2" value={title} onChange={e => setTitle(e.target.value)} placeholder="Title" />
//             <textarea className="form-control my-2" value={desc} onChange={e => setDesc(e.target.value)} placeholder="Description" />
//             <select className="form-select my-2" value={assignedTo} onChange={e => setAssignedTo(e.target.value)}>
//               <option value="">Select Employee</option>
//               {employees.map(emp => (
//                 <option key={emp.id} value={emp.id}>{emp.name}</option>
//               ))}
//             </select>
//             <button className="btn btn-primary w-100" onClick={assign}>
//               <i className="fas fa-plus-circle"></i> Assign
//             </button>
//           </div>

//           {/* All Works */}
//           <div className="card shadow-sm p-3">
//             <h5 className="text-info"><i className="fas fa-clipboard-list"></i> All Works</h5>
//             {works.map(w => (
//               <div key={w.id} className="border p-2 mb-2 rounded">
//                 <div className="d-flex justify-content-between">
//                   <strong>{w.title}</strong>
//                   <span className={`badge ${w.status === 'DONE' ? 'bg-success' : w.status === 'IN_PROGRESS' ? 'bg-warning' : 'bg-secondary'}`}>
//                     {w.status}
//                   </span>
//                 </div>
//                 <div className="text-muted">{w.description}</div>
//                 <div className="text-muted mt-1">Assigned: {w.assignedToName}</div>
//               </div>
//             ))}
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// }
