resource "aws_db_instance" "banking_db" {
  identifier        = "banking-db"
  engine            = "postgres"
  engine_version    = "14.7"
  instance_class    = "db.t3.medium"
  allocated_storage = 100
  
  db_name  = "bankingdb"
  username = "dbadmin"
  password = var.db_password
  
  vpc_security_group_ids = [aws_security_group.rds_sg.id]
  db_subnet_group_name   = aws_db_subnet_group.banking.id
  
  backup_retention_period = 7
  multi_az               = true
  
  tags = {
    Environment = "production"
    Project     = "banking"
  }
}