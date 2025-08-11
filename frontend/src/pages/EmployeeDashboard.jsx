import React, { useEffect, useState } from 'react';
import api from '../api';

export default function EmployeeDashboard() {
  const [works, setWorks] = useState([]);

  const fetch = async () => {
    const res = await api.get('/employee/works');
    setWorks(res.data);
  };

  useEffect(() => {
    fetch();
  }, []);

  const update = async (id, payload) => {
    await api.put(`/employee/works/${id}/update`, payload);
    fetch();
  };

  return (
    <div className="container mt-4">
      <h4 className="text-primary mb-4"><i className="fas fa-briefcase"></i> My Tasks</h4>
      {works.length === 0 && (
        <div className="card p-3 shadow-sm">No tasks assigned.</div>
      )}
      <div className="row">
        {works.map(w => (
          <div key={w.id} className="col-md-6">
            <div className="card shadow-sm p-3 mb-3">
              <div className="d-flex justify-content-between">
                <strong>{w.title}</strong>
                <span className={`badge ${w.status === 'DONE' ? 'bg-success' : w.status === 'IN_PROGRESS' ? 'bg-warning' : 'bg-secondary'}`}>
                  {w.status}
                </span>
              </div>
              <div className="text-muted">{w.description}</div>
              <textarea
                defaultValue={w.employeeUpdate}
                onBlur={e => update(w.id, { employeeUpdate: e.target.value })}
                className="form-control my-2"
                placeholder="Progress update"
              />
              <div className="d-flex gap-2">
                <button className="btn btn-warning btn-sm" onClick={() => update(w.id, { status: 'IN_PROGRESS' })}>
                  <i className="fas fa-spinner"></i> In Progress
                </button>
                <button className="btn btn-success btn-sm" onClick={() => update(w.id, { status: 'DONE' })}>
                  <i className="fas fa-check-circle"></i> Done
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
