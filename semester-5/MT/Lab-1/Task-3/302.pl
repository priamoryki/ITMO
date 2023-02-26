use strict;
use warnings FATAL => 'all';

my $result = "";
my $isStart = 1;
my $skipFlag = 0;

while (<>) {
    s/\<[^>]+\>//g;
    if ($isStart && !(s/^\s+$//)) {
        $isStart = 0;
    }
    if ($isStart) {
        next;
    }
    if (s/^\s+$//) {
        $skipFlag = 1;
        next;
    }
    if ($skipFlag) {
        $skipFlag = 0;
        $result .= "\n";
    }
    $_ =~ s/^(\s+)(\S+)(.*)$/$2$3/;
    $_ =~ s/^(.*)(\S+)(\s+)$/$1$2/;
    $_ =~ s/(\s+)/ /g;
    $result .= $_ . "\n";
}

print($result);
