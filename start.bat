set TODAY=%date%

### ���t�̕���
set /a h=%TODAY:~0,2%
set /a y=%TODAY:~2,2%
set m1=%TODAY:~5,1%
set m2=%TODAY:~6,1%
if "%m2%" equ "/" (
set /a m=m1
set /a d=%TODAY:~7,2%
) else (
set /a m=m1*10+m2
set /a d=%TODAY:~8,2%
)
if %m%==1 ( set /a y-=1&set /a m+=12 )
if %m%==2 ( set /a y-=1&set /a m+=12 )
  
### �c�F���[�̌���
set /a week_value=( y + (y/4) + (h/4) - 2*h + (13*(m+1)/5) + d ) %% 7
  
### �j���̐ݒ�
if %week_value%==0 set WEEK=�y
if %week_value%==1 set WEEK=��
if %week_value%==2 set WEEK=��
if %week_value%==3 set WEEK=��
if %week_value%==4 set WEEK=��
if %week_value%==5 set WEEK=��
if %week_value%==6 set WEEK=��

### ���Ԃ����A���A�b�ɕ�������
set /a hh=%time:~0,2%
set /a mn=%time:~3,2%
set /a ss=%time:~6,2%
### ���Ԃ̕���

echo result_%h%%y%_%m%_%d%(%WEEK%)_%hh%_%mn%_%ss%.xlsx

java -jar target\diffSellect-1.0-jar-with-dependencies.jar test\sql.txt result_%h%%y%_%m%_%d%(%WEEK%)_%hh%_%mn%_%ss%.xlsx

pause
