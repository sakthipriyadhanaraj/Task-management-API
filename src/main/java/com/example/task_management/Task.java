package com.example.task_management;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "tasks")
public class Task {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private String status;

        protected Task() {
        }
        public Task(String title) {
            this.title = title;
            this.status = "TODO";
        }

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getStatus() {
            return status;
        }
    public void setStatus(String status) {
        this.status = status;
    }

    }

