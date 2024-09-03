import smtplib
import dns.resolver
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from concurrent.futures import ThreadPoolExecutor, as_completed
import time

# Function to get MX records for a domain
def get_mx_records(domain):
    records = dns.resolver.resolve(domain, 'MX')
    mx_records = sorted(records, key=lambda record: record.preference)
    return [str(record.exchange) for record in mx_records]

# Function to send email via MX record with BCC
def send_email_via_mx(to_emails, from_email, from_name, subject, html_body):
    if not to_emails:
        return

    domain = to_emails[0].split('@')[1]
    mx_records = get_mx_records(domain)

    msg = MIMEMultipart()
    msg['Subject'] = subject
    msg['From'] = f"{from_name} <{from_email}>"
    msg['To'] = from_email  # To field can be any valid email address
    msg['Bcc'] = ', '.join(to_emails)
    msg.attach(MIMEText(html_body, 'html'))

    for mx in mx_records:
        try:
            with smtplib.SMTP(mx) as server:
                server.sendmail(from_email, to_emails, msg.as_string())
                print(f"Batch of {len(to_emails)} emails sent successfully via {mx}")
                # Store valid emails
                with open('valid.txt', 'a') as valid_file:
                    for email in to_emails:
                        valid_file.write(email + "\n")
                break
        except Exception as e:
            print(f"Failed to send batch of {len(to_emails)} emails via {mx}: {e}")
            # Store bounced emails
            with open('bounce.txt', 'a') as bounce_file:
                for email in to_emails:
                    bounce_file.write(email + "\n")

# Function to read email list from a file
def read_email_list(file_path):
    with open(file_path, 'r') as file:
        emails = [line.strip() for line in file if line.strip()]
    return emails

# Function to batch the email list
def batch_email_list(email_list, batch_size):
    for i in range(0, len(email_list), batch_size):
        yield email_list[i:i + batch_size]

# Function to read HTML content from a file
def read_html_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        return file.read()

# Email details
from_email = "support@essaystime.me"
from_name = "Tyson Team"
subject = "Don’t Miss Your Chance to Win $500 with USPS!"

# Read HTML content from file
html_body = read_html_file('email.html')

# Read email list from file
email_list = read_email_list('data.txt')

# Send email in batches of 100
batch_size = 100
batches = list(batch_email_list(email_list, batch_size))

# Use ThreadPoolExecutor to send emails concurrently
with ThreadPoolExecutor(max_workers=5) as executor:
    futures = []
    for batch in batches:
        futures.append(executor.submit(send_email_via_mx, batch, from_email, from_name, subject, html_body))
        time.sleep(3)  # Sleep for 3 seconds between sending each batch

    for future in as_completed(futures):
        try:
            future.result()
        except Exception as exc:
            print(f"Batch generated an exception: {exc}")
