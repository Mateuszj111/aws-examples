terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 4.16.0"
    }
  }
}

provider "aws" {
  region = "eu-central-1"
}

resource "aws_kinesis_stream" "test_stream" {
  name             = "${var.prefix}kinesis-test"
  shard_count      = 1
  retention_period = 24
  encryption_type  = "NONE"

  stream_mode_details {
    stream_mode = "PROVISIONED"
  }

  tags = {
    Environment = "dev"
  }
}

output "kinesis_stream_name" {
  value = aws_kinesis_stream.test_stream.name
}