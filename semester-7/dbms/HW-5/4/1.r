pi{StudentId, StudentName, GroupId}(Students)
diff
(pi{StudentId, StudentName, GroupId}(sigma{CourseName = :CourseName}(Students nj Marks nj Courses)))